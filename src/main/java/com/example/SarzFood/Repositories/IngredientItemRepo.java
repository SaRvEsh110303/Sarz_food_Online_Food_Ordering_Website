package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.IngredientItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepo extends JpaRepository<IngredientItems,Long> {
    List<IngredientItems> findByRestaurantId(Long id);

}
