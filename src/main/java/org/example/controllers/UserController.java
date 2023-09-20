package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.models.Id;
import org.example.models.user.User;
import org.example.models.user.UserResponse;
import org.example.models.user.mapper.UserEntityMapper;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "User controller", description = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final UserEntityMapper userEntityMapper;

    @Operation(summary = "Create new user", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "201",
            description = "User created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Id.class))})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Id createNewUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Get user by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "User found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @PreAuthorize("hasAnyAuthority('ADMIN', 'BUYER')")
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "User ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        return userEntityMapper.userToUserResponse(userService.findById(uuid));
    }

    @Operation(summary = "Update user by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "User updated")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @Operation(summary = "Delete user by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "204",
            description = "User deleted")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "User ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        userService.deleteUser(uuid);
    }
}
