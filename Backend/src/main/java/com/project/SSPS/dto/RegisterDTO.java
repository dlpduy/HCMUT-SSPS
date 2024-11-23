package com.project.SSPS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    @NotBlank(message = "Full name is required")
    private String fullName;

<<<<<<< HEAD
    @NotBlank(message = "Email is required")
    private String email;
=======
    @NotBlank(message = "Username is required")
    private String username;
>>>>>>> ea80ce5c64d519eab60320799c9c968febdad827

    @NotBlank(message = "Password is required")
    private String password;
}
