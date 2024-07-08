package com.rybina.TodoApplication.service;

import com.rybina.TodoApplication.model.Todo;
import com.rybina.TodoApplication.model.User;
import com.rybina.TodoApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<Todo> addTodoToUser(User user, Todo todo) {
        Objects.requireNonNull(todo);
        Objects.requireNonNull(user);

        Optional.ofNullable(todo.getUser()).ifPresentOrElse(User::addTodos, IllegalArgumentException::new);
        return Optional.of(todo);
    }

    public Optional<User> deleteById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse(userRepository::delete, IllegalArgumentException::new);

        return user;
    }

    public Optional<User> update(User updatedUser, Integer id){
        Optional<User> user = userRepository.findById(id);

        user.ifPresentOrElse((usr) -> {
            usr.setUsername(usr.getUsername());
            usr.setPassword(usr.getPassword());

            userRepository.save(usr);
        }, IllegalArgumentException::new);

        return user;
    }
}
