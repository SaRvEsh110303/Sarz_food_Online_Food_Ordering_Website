package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.Category;
import com.example.SarzFood.Entity.Food;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Repositories.CategoryRepo;
import com.example.SarzFood.Repositories.FoodRepo;
import com.example.SarzFood.Request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food =new Food();
        Category existingCategory = categoryRepo.findByName(category.getName());
        if (existingCategory == null) {
            existingCategory = categoryRepo.save(category);
        }
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setFoodName(req.getName());
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        Food save = foodRepo.save(food);
        restaurant.getFoods().add(save);
        return save;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food foodById = findFoodById(foodId);
        foodById.setRestaurant(null);
        foodRepo.save(foodById);
        foodRepo.deleteById(foodId);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean seasonal, String foodCategory) {
        List<Food> foods = foodRepo.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods=filterByVegetarian(foods,isVegetarian);
        }
        if(isNonveg){
            foods=filterByNonVeg(foods,isNonveg);

            if(seasonal){
                foods=filterBySeasonal(foods,seasonal);
            }
            if(foodCategory!=null && !foodCategory.equals("")){
                foods=filterByCategory(foods,foodCategory);
            }
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
     return foods.stream().filter(food -> {
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean seasonal) {
        return foods.stream().filter(food -> food.isSeasonal()== seasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonveg) {
    return foods.stream().filter(food -> food.isVegetarian()== false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }
    @Override
    public List<Food> searchFood(String keyword) {
        List<Food> foods = foodRepo.searchFood(keyword);
        return foods;
    }
    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food = foodRepo.findById(foodId);
        if(food.isEmpty()){
            throw new Exception("Food Not Exist....");
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food foodById = findFoodById(foodId);
        foodById.setAvailable(!foodById.isAvailable());
        Food save = foodRepo.save(foodById);
        return save;
    }
}
