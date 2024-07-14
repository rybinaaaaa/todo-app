package com.rybina.TodoApplication.service;

import com.rybina.TodoApplication.exception.repositoryException.NotSavedException;
import com.rybina.TodoApplication.model.Todo;
import com.rybina.TodoApplication.model.User;
import com.rybina.TodoApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) throws NotSavedException {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new NotSavedException(e.getMessage());
        }
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public Optional<Todo> addTodoToUser(User user, Todo todo) {
        Objects.requireNonNull(todo);
        Objects.requireNonNull(user);

        Optional.ofNullable(todo.getUser()).ifPresentOrElse(User::addTodos, IllegalArgumentException::new);
        return Optional.of(todo);
    }

    @Transactional
    public Optional<User> deleteById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse(userRepository::delete, IllegalArgumentException::new);

        return user;
    }

    @Transactional
    public Optional<User> update(User updatedUser, Integer id) {
        Optional<User> user = userRepository.findById(id);

        user.ifPresentOrElse((usr) -> {
            usr.setUsername(usr.getUsername());
            usr.setPassword(usr.getPassword());

            userRepository.save(usr);
        }, IllegalArgumentException::new);

        return user;
    }
}
