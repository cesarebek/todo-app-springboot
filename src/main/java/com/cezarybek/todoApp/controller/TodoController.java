package com.cezarybek.todoApp.controller;

import com.cezarybek.todoApp.model.Todo;
import com.cezarybek.todoApp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/all")
    public List<Todo> getAll() {
        return todoService.getAllUserTodos();
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodo(@PathVariable Integer id) {
        return todoService.getTodo(id);
    }

    @PostMapping("/save")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Integer id) {
        return todoService.deleteTodo(id);
    }
}
