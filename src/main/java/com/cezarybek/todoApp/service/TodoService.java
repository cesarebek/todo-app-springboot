package com.cezarybek.todoApp.service;

import com.cezarybek.todoApp.exception.TodoAppException;
import com.cezarybek.todoApp.model.Todo;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.TodoRepository;
import com.cezarybek.todoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        //Retrieving current authenticated user from Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //Loading the User -> id
        User user = userRepository.getByUsername(username);
        return user;
    }

    public List<Todo> getAllUserTodos() {
        //Retrieving User todos
        return todoRepository.getTodosByUserId(getCurrentUser().getId());
    }

    public Todo addTodo(Todo todo) {
        todo.setUserId(getCurrentUser().getId());
        return todoRepository.save(todo);
    }

    public ResponseEntity<String> deleteTodo(Integer id) {
        User user = getCurrentUser();
        System.out.println(user.getRoles().contains("ROLE_ADMIN"));
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("Todo with ID %s not found.", id));
        } else if (todo.get().getUserId() == user.getId() || user.getRoles().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN"))) {
            try {
                todoRepository.deleteById(id);
                return new ResponseEntity<>(String.format("Todo with ID %s successfully deleted", id), HttpStatus.OK);
            } catch (Exception e) {
                throw e;
            }
        }
        throw new TodoAppException(HttpStatus.FORBIDDEN, "You are not allowed to manage this content!");


    }

    public Optional<Todo> getTodo(Integer id) {
        User user = getCurrentUser();
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("Todo with ID %s not found.", id));
        } else if (todo.get().getUserId() != user.getId()) {
            throw new TodoAppException(HttpStatus.FORBIDDEN, "You are not allowed to see this content!");
        }
        return todo;

    }

    public Todo updateTodo(Integer id, Todo todo) {
        User user = getCurrentUser();
        Optional<Todo> todoItem = todoRepository.findById(id);
        if (todoItem.isEmpty()) {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("Todo with ID %s not found.", id));
        } else if (todoItem.get().getUserId() == user.getId() || user.getRoles().stream().anyMatch(a -> a.getName() == "ROLE_ADMIN")) {

            if (todo.getTitle() != null || todo.getTitle().equals("")) {
                todoItem.get().setTitle(todo.getTitle());
            }

            if (todo.getDescription() != null) {
                todoItem.get().setDescription(todo.getDescription());
            }

            if (todo.getIsCompleted() != null) {
                todoItem.get().setIsCompleted(todo.getIsCompleted());
            }


            return todoRepository.save(todoItem.get());
        }
        throw new TodoAppException(HttpStatus.FORBIDDEN, "You are not allowed to see this content!");

    }
}
