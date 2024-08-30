package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    public List<Order>findByCustomerId(Long userId);

    public List<Order> findByRestaurantId(Long restaurantId);
}
