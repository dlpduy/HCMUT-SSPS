package com.project.SSPS.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationDTO {

    @NotNull(message = "username can not be null")
    @Size(min = 5, message = "userName must be at least 5 characters")
    String username;

    @NotNull(message = "email can not be null")
    @Email
    String email;

    @Size(min = 6, max = 16, message = "password at least 6 characters")
    @NotNull(message = "password cannot be null")
    String password;

}
