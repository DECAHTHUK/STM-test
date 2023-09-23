package com.example.security;

import com.example.models.user.UserDto;
import com.example.security.models.LoginRequest;
import com.example.security.models.LoginResponse;
import com.example.security.models.RefreshJwtRequest;
import com.example.security.models.Role;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.models.user.mapper.UserEntityMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login controller", description = "Login API")
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    private final UserService userService;

    private final UserEntityMapper userEntityMapper;

    @Operation(summary = "Log in with creds", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Register new user", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "201",
            description = "Register successful")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(
                userEntityMapper.userDtoToUser(userDto).toBuilder()
                        .userRole(Role.BUYER).build());
    }

    @Operation(summary = "Refresh access token with refresh token", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Refresh successful",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))})
    @PostMapping("/access")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @Operation(summary = "Update refresh token", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Update successful",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))})
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        return authService.getNewRefreshToken(request.getRefreshToken());
    }
}
