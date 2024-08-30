package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.IngredientCategory;
import com.example.SarzFood.Entity.IngredientItems;

import java.util.List;

public interface IngredientsService {
    public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id)throws Exception;

    public List<IngredientCategory>findIngredientCategoryByRestaurantId(Long id)throws Exception;
    public IngredientItems createIngredientItem(Long restaurantId,String ingredientName,Long categoryId)throws Exception;
    public List<IngredientItems> findRestaurantsIngredients(Long restaurantId)throws Exception;

    public IngredientItems updateStock(Long id)throws Exception;

}
