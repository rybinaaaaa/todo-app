package com.rybina.TodoApplication.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@ToString(exclude = "user")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private LocalDate targetDate;

    private Boolean isDone;

    @JoinColumn(referencedColumnName = "id", name = "user_id")
    @ManyToOne
    private User user;
}
