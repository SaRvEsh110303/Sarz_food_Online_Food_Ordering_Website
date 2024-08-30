package com.example.SarzFood.Services;

import com.example.SarzFood.Entity.Cart;
import com.example.SarzFood.Entity.CartItem;
import com.example.SarzFood.Entity.User;
import com.example.SarzFood.Request.AddCartItemRequest;

import javax.swing.plaf.PanelUI;

public interface CartService {
public CartItem addItemToCart(AddCartItemRequest req, String jwt)throws Exception;
public CartItem updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;
public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;
public Long calculateCartTotals(Cart cart) throws Exception;
public Cart findCartById(Long id) throws Exception;
public Cart findCartByUserId(Long userId)throws Exception;
public Cart clearCart(Long userId)throws Exception;

}
