package com.example.SarzFood.Controller;

import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @GetMapping
    public String test() {
        System.out.println("Endpoint was hit!"); // Debugging line
        return "Got It";
    }
}
