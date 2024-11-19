package com.project.SSPS.mapper;


// TODO: The class Student is not implemented yet. Implement the class Student with the following fields: id, name, email, and age. The class should be in the model package.

import com.project.SSPS.dto.UserCreationDTO;
import com.project.SSPS.model.User;

public class StudentMapper {
    public User toUser(UserCreationDTO userCreationDTO) {
        User user = new User();
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(userCreationDTO.getPassword());
        user.setEmail(userCreationDTO.getEmail());
        // Set other properties as needed
        return user;
    }
}