package com.example.SarzFood.Services;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.SarzFood.Entity.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String username) throws Exception;

}
