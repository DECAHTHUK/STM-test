package com.example.service.impl;

import com.example.exceptions.models.CreationException;
import com.example.exceptions.models.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.repository.UserMapper;
import com.example.models.Id;
import com.example.models.user.User;
import com.example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Id createUser(User user) {
        if (userMapper.selectUserByEmail(user.getEmail()) != null) {
            throw new CreationException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insertUser(user);
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
        User userFromDb = userMapper.selectUserByEmail(user.getEmail());
        if (userFromDb != null && !userFromDb.getId().equals(user.getId())) {
            throw new CreationException("User with email " + user.getEmail() + " already exists");
        }
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