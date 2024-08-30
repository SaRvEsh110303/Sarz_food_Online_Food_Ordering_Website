package com.example.SarzFood.Controller;

import com.example.SarzFood.DTOs.RestaurantDto;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Response.FavouriteDeletedResponse;
import com.example.SarzFood.Services.RestaurantService;
import com.example.SarzFood.Services.UserService;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization")String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<RestaurantDto> addToFavourite(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto dto = restaurantService.addToFavourites(id, user);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/{id}/remove-favourites")
    public ResponseEntity<FavouriteDeletedResponse> RemoveFromFavourite(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        restaurantService.RemoveAddToFavourites(id,user);
        FavouriteDeletedResponse deletedResponse=new FavouriteDeletedResponse();
        deletedResponse.setMessage("One Restaurant From Favourites Deleted Successfully");
        return new ResponseEntity<>(deletedResponse,HttpStatus.OK);
    }
}
