package com.cezarybek.todoApp.controller;

import com.cezarybek.todoApp.model.Todo;
import com.cezarybek.todoApp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String hello(){
        return "Hello!";
    }

    @GetMapping("/getAll")
    public Iterable<Todo> getAll(){
        return todoService.getAllTodos();
    }

    @GetMapping("/getTodo/{id}")
    public Optional<Todo> getTodo(@PathVariable Integer id){
        return todoService.getTodo(id);
    }

    @PostMapping("/addTodo")
    public Todo addTodo(@RequestBody Todo todo){
        return todoService.addTodo(todo);
    }

    @DeleteMapping("/deleteTodo/{id}")
    public String deleteTodo(@PathVariable Integer id){
        return todoService.deleteTodo(id);
    }
}
