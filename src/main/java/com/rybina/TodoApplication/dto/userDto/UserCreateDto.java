package com.rybina.TodoApplication.dto.userDto;

import lombok.Data;

@Data
public class UserCreateDto {

    private String email;

    private String username;

    private String password;
}
