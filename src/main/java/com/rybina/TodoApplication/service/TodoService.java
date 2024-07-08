package com.rybina.TodoApplication.service;

import com.rybina.TodoApplication.model.Todo;
import com.rybina.TodoApplication.model.User;
import com.rybina.TodoApplication.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Optional<Todo> findById(Integer id) {
        return todoRepository.findById(id);
    }

    public Page<Todo> findAll(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    public Optional<Todo> deleteById(Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);

        todo.ifPresentOrElse(todoRepository::delete, IllegalArgumentException::new);
        return todo;
    }
}
