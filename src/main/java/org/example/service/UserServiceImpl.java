package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.models.CreationException;
import org.example.exceptions.models.NotFoundException;
import org.example.mapping.UserMapper;
import org.example.models.Id;
import org.example.models.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Id createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userMapper.insertUser(user);
        } catch (Exception e) {
            throw new CreationException("Error creating this user: " + e.getMessage());
        }
    }

    @Override
    public User findById(UUID uuid) {
        User user = userMapper.selectUser(uuid);
        if (user == null) {
            throw new NotFoundException("User with id = " + uuid + " was not found");
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userMapper.selectUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with email = " + email + " was not found");
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userMapper.deleteUser(id);
    }

    @Override
    public String getRefreshToken(String email) {
        return userMapper.selectRefreshToken(email);
    }

    @Override
    public void updateRefreshToken(String email, String refreshToken) {
        userMapper.updateRefreshToken(email, refreshToken);
    }
}