package org.example.security;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.models.AuthException;
import org.example.models.user.User;
import org.example.security.models.JwtAuthentication;
import org.example.security.models.LoginRequest;
import org.example.security.models.LoginResponse;
import org.example.security.utils.JwtProvider;
import org.example.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(@NonNull LoginRequest loginRequest) {
        final User user = userService.findByEmail(loginRequest.getEmail());
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            userService.updateRefreshToken(user.getEmail(), refreshToken);
            return new LoginResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password");
        }
    }

    public LoginResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = userService.getRefreshToken(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findByEmail(email);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new LoginResponse(accessToken, null);
            }
        }
        return new LoginResponse(null, null);
    }

    public LoginResponse getNewRefreshToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = userService.getRefreshToken(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findByEmail(email);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                userService.updateRefreshToken(email, newRefreshToken);
                return new LoginResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Wrong refresh token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}