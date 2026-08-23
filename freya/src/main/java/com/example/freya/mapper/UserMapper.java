package com.example.freya.mapper;

import com.example.freya.dtos.user.CreateUserDTO;
import com.example.freya.dtos.user.UserDTO;
import com.example.freya.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User createUserDTOToUser(CreateUserDTO dto);
    UserDTO userToUserDTO(User user);
}
