package com.realestate.service;

import com.realestate.model.User;
import java.util.Optional;

public interface IUserService {
    Optional<User> login(String email, String password);
    void logout(String email); // no-op for now, placeholder for sessions/jwt
}
