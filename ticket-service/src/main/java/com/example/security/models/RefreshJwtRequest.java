package com.example.security.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    @Schema(description = "Refresh token", example = "xxxxx.yyyyy.zzzzz")
    public String refreshToken;
}
