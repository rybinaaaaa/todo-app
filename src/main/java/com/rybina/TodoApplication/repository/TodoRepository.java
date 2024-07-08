package com.rybina.TodoApplication.repository;


import com.rybina.TodoApplication.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    Page<Todo> findAllUserId(Integer userId);
    Page<Todo> findAll(Pageable pageable);
}
