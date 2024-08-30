package com.example.SarzFood.Request;

import com.example.SarzFood.Entity.Category;
import lombok.Data;

@Data
public class IngredientItemRequest {
    private String name;
    private Long categoryId;
    private Long restaurantId;

}
