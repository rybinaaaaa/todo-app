package com.rybina.TodoApplication.dto.userDto;

import com.rybina.TodoApplication.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserReadDto toReadDto(User user) {
        UserReadDto userReadDto = new UserReadDto();
        userReadDto.setId(user.getId());
        userReadDto.setEmail(user.getEmail());
        userReadDto.setUsername(user.getUsername());

        return userReadDto;
    }

    public User fromCreateDto(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());
        user.setUsername(userCreateDto.getUsername());

        return user;
    }

    public User copyFromReadEditDto(UserReadDto userReadDto, User user) {
        user.setUsername(userReadDto.getUsername());
        user.setEmail(userReadDto.getEmail());

        return user;
    }
}
