package com.rybina.TodoApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Todo> todoList = new ArrayList<>();

    public void addTodos(Todo... todos) {
        Arrays.stream(todos).forEach((todo) -> {
            todo.setUser(this);
            todoList.add(todo);
        });
    }

    public void removeTodo(Todo... todos) {
        Arrays.asList(todos).forEach((todo) -> {
            if (!todoList.contains(todo)) throw new IllegalArgumentException();
            else {
                todoList.remove(todo);
                todo.setUser(null);
            }
        });
    }
}
