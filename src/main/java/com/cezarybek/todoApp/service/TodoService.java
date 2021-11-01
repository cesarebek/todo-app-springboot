package com.cezarybek.todoApp.service;

import com.cezarybek.todoApp.model.Todo;
import com.cezarybek.todoApp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;


    public List<Todo> getAllUserTodos(Integer user_id) {
        return todoRepository.getTodosByUserId(user_id);
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public String deleteTodo(Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (!todo.isEmpty()) {
            todoRepository.deleteById(id);
            return "Todo with id " + id + " deleted!";
        } else {
            return "Todo with id " + id + " not exist!";
        }
    }

    public Optional<Todo> getTodo(Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (!todo.isEmpty()) {
            return todo;
        } else {
            throw new NullPointerException();
        }

    }
}
