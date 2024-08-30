package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.Category;
import com.example.SarzFood.Entity.Restaurant;
import com.example.SarzFood.Repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category=new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepo.save(category);
    }
    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurantById = restaurantService.getRestaurantByUserId(id);
        return categoryRepo.findByRestaurantId(restaurantById.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if(optionalCategory.isEmpty()){
            throw new Exception("Category Not Found");
        }
        return optionalCategory.get();
    }
}
