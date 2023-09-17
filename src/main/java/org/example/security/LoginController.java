package org.example.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.models.user.UserDto;
import org.example.models.user.mapper.UserEntityMapper;
import org.example.security.models.JwtAuthentication;
import org.example.security.models.LoginRequest;
import org.example.security.models.LoginResponse;
import org.example.security.models.RefreshJwtRequest;
import org.example.security.models.Role;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    private final UserService userService;

    private final UserEntityMapper userEntityMapper;

    @PostMapping
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(
                userEntityMapper.userDtoToUser(userDto).toBuilder()
                        .userRole(Role.USER.toString()).build());
    }

    @PostMapping("/access")
    public LoginResponse getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("/refresh")
    public LoginResponse getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        return authService.getNewRefreshToken(request.getRefreshToken());
    }

    @GetMapping("/hello/user")
    public String helloUser() {
        return "User";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/hello/admin")
    public String helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return authInfo.getPrincipal().toString();
    }
}
