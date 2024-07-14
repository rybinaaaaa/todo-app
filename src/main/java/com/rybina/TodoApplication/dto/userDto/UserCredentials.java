package com.rybina.TodoApplication.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UserCredentials {

    @NotBlank(message = "Username should not be empty")
    String username;

    @NotBlank(message = "Password should not be empty")
    String password;
}
