package com.example.SarzFood.Controller;

import com.example.SarzFood.Entity.Food;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Request.CreateFoodRequest;
import com.example.SarzFood.Response.MessageResponse;
import com.example.SarzFood.Services.FoodService;
import com.example.SarzFood.Services.RestaurantService;
import com.example.SarzFood.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestScope;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurantById = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurantById);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setMessage("Food Deleted SuccessFully...");
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailability(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food,HttpStatus.OK);
    }

}
