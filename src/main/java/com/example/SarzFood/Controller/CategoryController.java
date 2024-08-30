package com.example.SarzFood.Controller;

import com.example.SarzFood.Entity.Category;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Services.CategoryService;
import com.example.SarzFood.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category>createCategory(@RequestBody Category category,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Category> categoryByRestaurantId = categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categoryByRestaurantId,HttpStatus.OK);
    }
}
