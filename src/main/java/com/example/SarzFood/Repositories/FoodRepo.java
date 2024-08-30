package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface FoodRepo extends JpaRepository<Food,Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food f WHERE f.foodName LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
    List<Food> searchFood(@Param("keyword")String keyword);
}
