package com.example.SarzFood.Request;

import com.example.SarzFood.Entity.Address;
import com.example.SarzFood.Entity.ContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfo contactInformation;
    private String openingHours;
    private List<String> images;
}
