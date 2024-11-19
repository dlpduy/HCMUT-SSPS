package com.project.SSPS.mapper;

import com.project.SSPS.dto.UserCreationDTO;
import com.project.SSPS.model.User;

public class AdminMapper {
    public User toUser(UserCreationDTO userCreationDTO) {
        User user = new User();
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(userCreationDTO.getPassword());
        user.setEmail(userCreationDTO.getEmail());
        // Set other properties as needed
        return user;
    }
}