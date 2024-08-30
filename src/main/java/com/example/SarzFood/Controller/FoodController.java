package com.example.SarzFood.Controller;

import com.example.SarzFood.Entity.Food;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Services.FoodService;
import com.example.SarzFood.Services.RestaurantService;
import com.example.SarzFood.Services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @GetMapping("/search")
    public ResponseEntity<List<Food>>createFood(@RequestParam String name,
                                          @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>>getRestaurantFood(
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @RequestParam(required = false)String food_category,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> restaurantsFood = foodService.getRestaurantsFood(restaurantId, vegetarian, nonveg, seasonal, food_category);
        return new ResponseEntity<>(restaurantsFood,HttpStatus.OK);

    }
}
