package com.example.security.utils;

import com.example.security.models.JwtAuthentication;
import com.example.security.models.Role;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(Set.of(Role.valueOf(claims.get("role", String.class))));
        jwtInfoToken.setFullName(claims.get("fullName", String.class));
        jwtInfoToken.setUsername(claims.get("id", String.class));
        return jwtInfoToken;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}