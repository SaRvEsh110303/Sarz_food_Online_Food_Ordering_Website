package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Long> {
    public Cart findByCustomerId(Long userId);
}
