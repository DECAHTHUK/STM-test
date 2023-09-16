package org.example.security;

import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.models.User;
import org.example.security.models.JwtAuthentication;
import org.example.security.models.LoginRequest;
import org.example.security.utils.JwtProvider;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public String login(@NonNull LoginRequest authRequest) {
        final User user = userService.findByEmail(authRequest.getEmail());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return jwtProvider.generateAccessToken(user);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong password");
        }
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}