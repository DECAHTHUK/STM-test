package com.example.models.user;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UserDto {
    @Email(message = "Enter valid email.")
    private String email;

    @Length(min = 6, max = 128, message = "Password should be between 6 and 128 characters long.")
    private String password;

    @Length(min = 3, max = 128, message = "Full name should be between 3 and 128 characters long.")
    private String fullName;
}
