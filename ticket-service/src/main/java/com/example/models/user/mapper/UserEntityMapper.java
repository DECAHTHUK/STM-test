package com.example.models.user.mapper;

import com.example.models.user.User;
import com.example.models.user.UserDto;
import com.example.models.user.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserResponse userToUserResponse(User user);

    User userDtoToUser(UserDto userDto);
}
