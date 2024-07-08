package com.rybina.TodoApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    private LocalDate targetDate;

    private Boolean isDone;

    @JoinColumn(referencedColumnName = "id", name = "user_id")
    @ManyToOne
    private User user;
}
