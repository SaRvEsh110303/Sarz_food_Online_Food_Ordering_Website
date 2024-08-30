package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.Cart;
import com.example.SarzFood.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {

}
