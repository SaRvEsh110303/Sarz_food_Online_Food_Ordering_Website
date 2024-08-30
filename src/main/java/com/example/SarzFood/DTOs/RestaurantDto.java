package com.example.SarzFood.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
@Embeddable
@Data
public class RestaurantDto {
    private String title;
    @Column(length = 1000)
    private List<String> images;
    private String Description;
}
