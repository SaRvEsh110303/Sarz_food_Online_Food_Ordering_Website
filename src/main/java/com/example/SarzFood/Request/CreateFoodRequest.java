package com.example.SarzFood.Request;

import com.example.SarzFood.Entity.Category;
import com.example.SarzFood.Entity.IngredientItems;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientItems> ingredients;
}
