package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepo extends JpaRepository<IngredientCategory,Long> {
    List<IngredientCategory>findByRestaurantId(Long id);

}
