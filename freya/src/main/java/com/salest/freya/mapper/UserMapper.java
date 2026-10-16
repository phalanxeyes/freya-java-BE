package com.salest.freya.mapper;

import com.salest.freya.dtos.user.CreateUserDTO;
import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.dtos.user.UserSignUpDTO;
import com.salest.freya.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
}
