package com.example.SarzFood.Request;

import com.example.SarzFood.Entity.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
