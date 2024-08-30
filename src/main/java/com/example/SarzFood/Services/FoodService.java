package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.Category;
import com.example.SarzFood.Entity.Food;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
     void deleteFood(Long foodId) throws Exception;
    public List<Food>getRestaurantsFood(Long restaurantId,boolean isVegetarian,
                                        boolean isNonveg,boolean seasonal,
                                        String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId)throws Exception;
    public Food updateAvailabilityStatus(Long foodId)throws Exception;
}
