package org.example.models.user.mapper;

import org.example.models.user.User;
import org.example.models.user.UserDto;
import org.example.models.user.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserResponse userToUserResponse(User user);

    User userDtoToUser(UserDto userDto);
}
