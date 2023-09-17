package org.example.models.user;

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
