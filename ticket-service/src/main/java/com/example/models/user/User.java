package com.example.models.user;

import com.example.security.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Schema(description = "Id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    private String id;

    @Email(message = "Enter valid email.")
    @Schema(description = "Email", example = "user@test.com")
    private String email;

    @Length(min = 6, max = 128, message = "Password should be between 6 and 128 characters long.")
    @Schema(description = "Password", example = "12345678")
    private String password;

    @Length(min = 3, max = 128, message = "Full name should be between 3 and 128 characters long.")
    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    @Schema(description = "Role(ADMIN/BUYER)", example = "ADMIN")
    private Role userRole;

    public User(String email, String password, String fullName, Role userRole) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userRole = userRole;
    }
}
