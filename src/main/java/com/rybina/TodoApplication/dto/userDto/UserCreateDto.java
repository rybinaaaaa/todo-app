package com.rybina.TodoApplication.dto.userDto;

import com.rybina.TodoApplication.dto.validator.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {

    @Email(message = "Incorrect email")
    @NotBlank(message = "Incorrect email")
    private String email;

    @UniqueUsername
    @NotBlank(message = "Username should not be empty")
    private String username;

    @Size(min = 3, message = "Password should contain at least 3 symbols!")
    private String password;
}
