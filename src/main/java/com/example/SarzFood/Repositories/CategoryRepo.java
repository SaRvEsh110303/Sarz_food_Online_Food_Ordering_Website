package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {

    public List<Category> findByRestaurantId(Long id);
    Category findByName(String name);

}
