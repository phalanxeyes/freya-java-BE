package com.salest.freya.mapper;

import com.salest.freya.dtos.user.CreateUserDTO;
import com.salest.freya.dtos.user.UserDTO;
import com.salest.freya.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User createUserDTOToUser(CreateUserDTO dto);
    UserDTO userToUserDTO(User user);
}
