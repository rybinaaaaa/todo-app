package com.rybina.TodoApplication.dto.validator;

import com.rybina.TodoApplication.dto.userDto.UserReadDto;
import com.rybina.TodoApplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;
    private final HttpSession httpSession;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(httpSession.getAttribute("user"))
                .map(user -> {
                    if (user instanceof UserReadDto) {
                        String currentUserUsername = ((UserReadDto) user).getUsername();
                        return currentUserUsername.equals(value) || userService.findByUsername(value).isEmpty();
                    }
                    return false;
                })
                .orElseGet(() -> userService.findByUsername(value).isEmpty());
    }
}
