package com.rybina.TodoApplication.dto.userDto;

import com.rybina.TodoApplication.dto.validator.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Value;

@Data
public class UserReadDto {

    private Integer id;

    @Email(message = "Incorrect email")
    private String email;

    @NotBlank(message = "Username should not be empty")
    @UniqueUsername
    private String username;
}
