package com.example.SarzFood.Services;

import com.example.SarzFood.Config.JwtProvider;
import com.example.SarzFood.Config.JwtTokenValidator;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Repositories.UserRepository;
import org.hibernate.boot.model.process.internal.UserTypeResolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String username) throws Exception {
        User user = userRepository.findByEmail(username);
        if(user==null){
            throw new Exception("User Not Found");
        }
        return user;
    }
}
