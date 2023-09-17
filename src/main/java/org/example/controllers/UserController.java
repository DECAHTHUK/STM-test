package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.models.Id;
import org.example.models.user.User;
import org.example.models.user.UserResponse;
import org.example.models.user.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public Id createNewUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{uuid}")
    public UserResponse getUserById(@PathVariable UUID uuid) {
        return userMapper.userToUserResponse(userService.findById(uuid));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{uuid}")
    public void deleteUserById(@PathVariable UUID uuid) {
        userService.deleteUser(uuid);
    }
}
