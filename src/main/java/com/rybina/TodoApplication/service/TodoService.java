package com.rybina.TodoApplication.service;

import com.rybina.TodoApplication.exception.repositoryException.NotSavedException;
import com.rybina.TodoApplication.model.Todo;
import com.rybina.TodoApplication.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public Todo save(Todo todo) throws NotSavedException {
        try {
            return todoRepository.save(todo);
        } catch (Exception e) {
            throw new NotSavedException(e.getMessage());
        }
    }

    public Optional<Todo> findById(Integer id) {
        return todoRepository.findById(id);
    }

    public Page<Todo> findByUserId(Integer userId, Pageable pageable) {
        return todoRepository.findByUserId(userId, pageable);
    }

    public Page<Todo> findAll(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @Transactional
    public Optional<Todo> deleteById(Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);

        todo.ifPresentOrElse(todoRepository::delete, IllegalArgumentException::new);
        return todo;
    }
}
