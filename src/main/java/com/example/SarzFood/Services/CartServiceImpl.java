package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.Cart;
import com.example.SarzFood.Entity.CartItem;
import com.example.SarzFood.Entity.Food;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Repositories.CartItemRepo;
import com.example.SarzFood.Repositories.CartRepo;
import com.example.SarzFood.Repositories.FoodRepo;
import com.example.SarzFood.Request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepo.findByCustomerId(user.getId());
        for(CartItem cartItem: cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
         CartItem cartItem=new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity()*food.getPrice());
        CartItem newCartItem = cartItemRepo.save(cartItem);
        cart.getItem().add(newCartItem);
        return newCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> opt = cartItemRepo.findById(cartItemId);
        if(opt.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItem cartItem = opt.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice((cartItem.getFood().getPrice())*quantity);
        return cartItemRepo.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepo.findByCustomerId(user.getId());
        Optional<CartItem> opt = cartItemRepo.findById(cartItemId);
        if(opt.isEmpty()){
            throw new Exception("Cart Item Not Found");
        }
        CartItem cartItem = opt.get();
        cart.getItem().remove(cartItem);
        return cartRepo.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total=0L;
        for(CartItem cartItem: cart.getItem()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> opt = cartRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Cart Not Found");
        }
        return opt.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepo.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
        public Cart clearCart(Long userId) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepo.save(cart);
    }
}
