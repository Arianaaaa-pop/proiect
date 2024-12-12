package com.example.user_ms.dtos.builders;

import com.example.user_ms.dtos.UserDTO;
import com.example.user_ms.entities.User;
import org.modelmapper.ModelMapper;

public class UserBuilder {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO toUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
    public static User toEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

}
