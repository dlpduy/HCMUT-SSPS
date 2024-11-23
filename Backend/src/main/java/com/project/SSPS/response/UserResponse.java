package com.project.SSPS.response;

import com.project.SSPS.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
<<<<<<< HEAD
    private String email;
    private String phoneNumber;
    private String address;
=======
    private String username;
>>>>>>> ea80ce5c64d519eab60320799c9c968febdad827
    private Role role;
}
