package org.example.models.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String id;

    private String email;

    private String fullName;

    private String userRole;
}
