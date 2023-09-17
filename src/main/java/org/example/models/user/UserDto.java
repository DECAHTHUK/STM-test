package org.example.models.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UserDto {
    private String id;

    private String email;

    @Length(min = 6)
    private String password;

    private String fullName;
}
