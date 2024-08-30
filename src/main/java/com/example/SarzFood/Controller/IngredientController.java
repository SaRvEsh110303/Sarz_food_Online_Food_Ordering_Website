package com.example.SarzFood.Controller;

import com.example.SarzFood.Entity.IngredientCategory;
import com.example.SarzFood.Entity.IngredientItems;
import com.example.SarzFood.Request.IngredientCategoryRequest;
import com.example.SarzFood.Request.IngredientItemRequest;
import com.example.SarzFood.Services.IngredientsService;
import com.fasterxml.jackson.core.PrettyPrinter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
    ) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);

    }
    @PostMapping()
    public ResponseEntity<IngredientItems> createIngredientItem(
            @RequestBody IngredientItemRequest req
            ) throws Exception {
        IngredientItems ingredientItem = ingredientsService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());        return new ResponseEntity<>(ingredientItem,HttpStatus.CREATED);
    }
    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientItems> updateStocks(
            @PathVariable Long id
    ) throws Exception {
        IngredientItems ingredientItems = ingredientsService.updateStock(id);
        return new ResponseEntity<>(ingredientItems,HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientItems>> getRestaurantIngredients(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientItems> restaurantsIngredients = ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(restaurantsIngredients,HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantsIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> ingredientCategoryById = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategoryById,HttpStatus.OK);
    }
}
