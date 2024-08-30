package com.example.SarzFood.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodName;
    private String description;
    private Long price;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category foodCategory;

    private List<String> images;

    private boolean available;
    @ManyToOne
    private Restaurant restaurant;

    private boolean isVegetarian;
    private boolean isSeasonal;
    @ManyToMany
    private List<IngredientItems> ingredients=new ArrayList<>();
    private Date creationDate;
}
