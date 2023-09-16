package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private String id;

    private String email;

    @Length(min = 6)
    private String password;

    private String fullName;

    private String userRole;

    public User(String email, String password, String fullName, String userRole) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userRole = userRole;
    }
}
