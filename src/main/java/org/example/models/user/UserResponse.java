package org.example.models.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.security.models.Role;

@Data
@NoArgsConstructor
public class UserResponse {
    @Schema(description = "Id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    private String id;

    @Schema(description = "Email", example = "user@test.com")
    private String email;

    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    @Schema(description = "Role(ADMIN/BUYER)", example = "ADMIN")
    private Role userRole;
}
