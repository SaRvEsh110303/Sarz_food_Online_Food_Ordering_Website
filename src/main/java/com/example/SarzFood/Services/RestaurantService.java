package com.example.SarzFood.Services;

import com.example.SarzFood.DTOs.RestaurantDto;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;
    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();
    public List<Restaurant> searchRestaurant(String keyword);
    public Restaurant findRestaurantById(Long id) throws Exception;
    public  Restaurant getRestaurantByUserId(Long userid) throws Exception;
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;
    public Restaurant updateRestaurantStatus(Long id) throws Exception;
    public void RemoveAddToFavourites(Long restaurantId, User user)throws Exception;
}
