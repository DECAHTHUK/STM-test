package org.example.security.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @Schema(description = "Email", example = "user@test.com")
    private String email;

    @Schema(description = "Password", example = "12345678")
    private String password;
}
