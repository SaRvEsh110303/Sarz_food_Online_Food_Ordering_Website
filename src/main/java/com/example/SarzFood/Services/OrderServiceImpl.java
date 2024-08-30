package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.*;
import com.example.SarzFood.Repositories.*;
import com.example.SarzFood.Request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepo.save(shippingAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurantById = restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder= new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("Pending");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurantById);
        Cart cart = cartService.findCartByUserId(user.getId());


        List<OrderItem>orderItem=new ArrayList<>();


        for(CartItem cartItem:cart.getItem()){
            OrderItem orderItems =new OrderItem();
            orderItems.setFood(cartItem.getFood());
            orderItems.setQuantity(cartItem.getQuantity());
            orderItems.setIngredients(cartItem.getIngredients());
            orderItems.setTotalPrice(cartItem.getTotalPrice());
            OrderItem saveOrderItem = orderItemRepo.save(orderItems);
            orderItem.add(saveOrderItem);
        }

    createdOrder.setItems(orderItem);
        Long cartTotals = cartService.calculateCartTotals(cart);
        createdOrder.setTotalPrice(cartTotals);
        Order save = orderRepo.save(createdOrder);
        restaurantById.getOrders().add(save);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order orderById = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                ||orderStatus.equals(("DELIVERED"))
                ||orderStatus.equals("COMPLETED")
                ||orderStatus.equals("PENDING")
         ){
            orderById.setOrderStatus(orderStatus);
            return orderRepo.save(orderById);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order orderById = findOrderById(orderId);
        orderRepo.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
      return orderRepo.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepo.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> opt = orderRepo.findById(orderId);
        if(opt.isEmpty()){
            throw new Exception("Order Not Found");
        }
        return opt.get();
    }
}
