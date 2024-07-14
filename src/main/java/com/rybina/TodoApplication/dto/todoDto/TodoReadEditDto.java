package com.rybina.TodoApplication.dto.todoDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TodoReadEditDto {

    private Integer id;

    private String title;

    private String description;

    private LocalDate targetDate;

    private Boolean isDone;

    private Integer userId;
}
