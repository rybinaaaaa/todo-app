package com.rybina.TodoApplication.dto.todoDto;

import com.rybina.TodoApplication.model.Todo;
import com.rybina.TodoApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TodoDtoMapper {

    private final UserService userService;

    public TodoReadEditDto mapToReadDto(Todo todo) {
        TodoReadEditDto todoReadEditDto = new TodoReadEditDto();
        todoReadEditDto.setId(todo.getId());
        todoReadEditDto.setTitle(todo.getTitle());
        todoReadEditDto.setDescription(todo.getDescription());
        todoReadEditDto.setTargetDate(todo.getTargetDate());
        todoReadEditDto.setIsDone(todo.getIsDone());

        return todoReadEditDto;
    }

    public Todo mapFromCreateDto(TodoCreateDto todoCreateDto, Integer userId) {
        Todo todo = new Todo();

        todo.setTitle(todoCreateDto.getTitle());
        todo.setDescription(todoCreateDto.getDescription());
        todo.setTargetDate(todoCreateDto.getTargetDate());
        todo.setIsDone(false);

        userService.findById(userId)
                .ifPresentOrElse(todo::setUser, IllegalArgumentException::new);

        return todo;
    }

    public Todo copyFromReadEditDto(TodoReadEditDto todoDto, Todo todo) {
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setTargetDate(todoDto.getTargetDate());
        todo.setIsDone(todoDto.getIsDone());

        return todo;
    }
}
