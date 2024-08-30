package com.example.SarzFood.Controller;

import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Request.CreateRestaurantRequest;
import com.example.SarzFood.Response.MessageResponse;
import com.example.SarzFood.Services.RestaurantService;
import com.example.SarzFood.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @PostMapping()
    public ResponseEntity<Restaurant>createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization")String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestHeader("Authorization")String jwt,
            @RequestBody CreateRestaurantRequest req,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse>deleteRestaurant(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
        MessageResponse message=new MessageResponse();
        message.setMessage("Restaurant Deleted Successfully");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long id
    ) throws Exception {
        User userByJwtToken = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<Restaurant>findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

}
