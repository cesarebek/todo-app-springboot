package com.cezarybek.todoApp.repository;

import com.cezarybek.todoApp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> getTodosByUserId(Integer user_id);
}
