package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.IngredientCategory;
import com.example.SarzFood.Entity.IngredientItems;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Repositories.IngredientCategoryRepo;
import com.example.SarzFood.Repositories.IngredientItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IngredientServiceImpl implements IngredientsService{
    @Autowired
    private IngredientItemRepo ingredientItemRepo;
    @Autowired
    private IngredientCategoryRepo ingredientCategoryRepo;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurantById = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory=new IngredientCategory();
        ingredientCategory.setRestaurant(restaurantById);
        ingredientCategory.setName(name);
        return ingredientCategoryRepo.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Ingredient Category Not Found....");
        }
        return opt.get();
    }
    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepo.findByRestaurantId(id);
    }

    @Override
    public IngredientItems createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategoryById = findIngredientCategoryById(categoryId);
        IngredientItems items=new IngredientItems();
        items.setName(ingredientName);
        items.setRestaurant(restaurant);
        items.setCategory(ingredientCategoryById);

        IngredientItems ingredient = ingredientItemRepo.save(items);
        ingredientCategoryById.getIngredientItems().add(ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientItems> findRestaurantsIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientItems updateStock(Long id) throws Exception {
        Optional<IngredientItems> opt = ingredientItemRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Ingredient Not Found...");
        }
        IngredientItems ingredientItems = opt.get();
        ingredientItems.setInStock(!ingredientItems.isInStock());
        return ingredientItemRepo.save(ingredientItems);
    }
}
