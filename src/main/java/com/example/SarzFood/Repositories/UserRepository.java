package com.example.SarzFood.Repositories;

import com.example.SarzFood.Entity.User;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String username);
}
