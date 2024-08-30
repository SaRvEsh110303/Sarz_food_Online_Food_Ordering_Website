package com.example.SarzFood.Controller;

import com.example.SarzFood.Config.JwtProvider;
import com.example.SarzFood.Entity.Cart;
import com.example.SarzFood.Entity.USER_ROLE;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Repositories.CartRepo;
import com.example.SarzFood.Repositories.UserRepository;
import com.example.SarzFood.Request.LoginRequest;
import com.example.SarzFood.Response.AuthResponse;
import com.example.SarzFood.Services.CustomerUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepo cartRepo;

    Logger logger= LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createHandler(@RequestBody User user) throws Exception {
        logger.info("Creating new user with email: {}", user.getEmail());

        User isEmailExist=userRepo.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            logger.error("Email already exists: {}", user.getEmail());
            throw new Exception("Email is already used with another account");
        }

        User createdUser=new User();

        createdUser.setFullName(user.getFullName());
        createdUser.setEmail(user.getEmail());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Saving user to database: {}", createdUser);
//        AtomicInteger lastAssignedId = new AtomicInteger(-1);

// Inside the handler method:
//        if (createdUser.getId() == null) {
//            int nextId = lastAssignedId.incrementAndGet();
//            createdUser.setId((long) nextId);
//        }

        User savedUser = userRepo.save(createdUser);
        logger.info("User saved successfully: {}", savedUser);

        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartRepo.save(cart);

        logger.info("Cart saved successfully");

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);

        logger.info("Generated JWT token: {}", jwt);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register Success");
        authResponse.setRole(savedUser.getRole());

        logger.info("Returning auth response: {}", authResponse);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){
        String username = req.getEmail();
        String password = req.getPassword();
        Authentication authentication=authenticate(username,password);

        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        String jwt=jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Success");
        authResponse.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails=customerUserDetailsService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username.....");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
