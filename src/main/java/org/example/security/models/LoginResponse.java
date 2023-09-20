package org.example.security.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    @Schema(example = "Bearer")
    private final String type = "Bearer";

    @Schema(description = "Access token", example = "xxxxx.yyyyy.zzzzz")
    private String accessToken;

    @Schema(description = "Refresh token", example = "xxxxx.yyyyy.zzzzz")
    private String refreshToken;
}
