package com.example.service;

import com.example.models.user.User;
import com.example.models.Id;

import java.util.UUID;

public interface UserService {
    Id createUser(User user);

    User findById(UUID uuid);

    User findByEmail(String email);

    void updateUser(User user);

    void deleteUser(UUID id);

    String getRefreshToken(String email);

    void updateRefreshToken(String email, String refreshToken);
}
