package com.rybina.TodoApplication.dto.userDto;

import lombok.Data;
import lombok.Value;

@Data
public class UserReadDto {

    private Integer id;

    private String username;

    private String email;
}
