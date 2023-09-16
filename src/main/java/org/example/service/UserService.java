package org.example.service;

import org.example.models.Id;
import org.example.models.User;

import java.util.UUID;

public interface UserService {
    Id createUser(User user);

    User findById(UUID uuid);

    User findByEmail(String email);

    void updateUser(User user);

    void deleteUser(UUID id);
}
