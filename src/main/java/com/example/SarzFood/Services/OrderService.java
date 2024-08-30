package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.Order;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Request.OrderRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest order, User user) throws Exception;
    public Order updateOrder(Long orderId,String orderStatus) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getUsersOrder(Long userId) throws Exception;
    public List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus) throws Exception;
    public Order findOrderById(Long orderId)throws Exception;
}
