package com.rybina.TodoApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();

    public void addTodos(Todo... todos) {
        todoList.addAll(Arrays.asList(todos));
    }

    public void removeTodo(Todo... todos) {
        Arrays.asList(todos).forEach((todo) -> {
            if (!todoList.contains(todo)) throw new IllegalArgumentException();
            else todoList.remove(todo);
        });
    }
}
