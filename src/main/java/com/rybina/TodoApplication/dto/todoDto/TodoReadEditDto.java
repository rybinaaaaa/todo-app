package com.rybina.TodoApplication.dto.todoDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TodoReadEditDto {

    private Integer id;

    @NotBlank(message = "Title should not be empty")
    private String title;

    @NotBlank
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    private Boolean isDone;
}
