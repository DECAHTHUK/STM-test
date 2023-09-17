package org.example.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
