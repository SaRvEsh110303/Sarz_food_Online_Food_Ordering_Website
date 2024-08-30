package com.example.SarzFood.Controller;

import com.example.SarzFood.Entity.Order;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Repositories.OrderRepo;
import com.example.SarzFood.Request.OrderRequest;
import com.example.SarzFood.Services.OrderService;
import com.example.SarzFood.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order>createOrder(@RequestBody OrderRequest req,
                                            @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> usersOrder = orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(usersOrder,HttpStatus.OK);
    }
}
