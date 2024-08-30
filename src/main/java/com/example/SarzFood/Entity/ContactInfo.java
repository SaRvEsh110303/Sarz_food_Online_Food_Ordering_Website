package com.example.SarzFood.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Embeddable
public class ContactInfo {
   private String email;
   private String mobile;
   private String twitter;
   private String instagram;
}
