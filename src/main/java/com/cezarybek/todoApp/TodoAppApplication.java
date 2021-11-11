package com.cezarybek.todoApp;

import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.Todo;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.TodoRepository;
import com.cezarybek.todoApp.service.RoleService;
import com.cezarybek.todoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppApplication {

    @Autowired
    private TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

    //    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            //Adding Roles to database
            roleService.addRole(new Role("ROLE_ADMIN", "Role for administrators of TODO API"));
            roleService.addRole(new Role("ROLE_USER", "Role for standard users of TODO API"));

            //Adding Users to database with default ROLE_USER
            userService.addUser(new User("cesare", "cesarebek1@gmail.com", "pass123"));
            userService.addUser(new User("elisa", "elisa1999@gmail.com", "pass123"));
            userService.addUser(new User("anna", "anna1969@gmail.com", "pass123"));
            userService.addUser(new User("italo", "italo@gmail.com", "pass123"));
            userService.addUser(new User("gabriele", "gabriele@gmail.com", "pass123"));
            userService.addUser(new User("stefano", "stefano@gmail.com", "pass123"));
            userService.addUser(new User("luca", "luca@gmail.com", "pass123"));
            userService.addUser(new User("francesca", "francesca@gmail.com", "pass123"));
            userService.addUser(new User("lucia", "lucia@gmail.com", "pass123"));
            userService.addUser(new User("luigino", "luigino@gmail.com", "pass123"));

            //Adding ROLE_ADMIN to Users
            userService.addRoleToUser("cesare", "ROLE_ADMIN");

            //Adding Todos to Users
            todoRepository.save(new Todo(null, 1, "Todo 1", "Todo description 1", false));
            todoRepository.save(new Todo(null, 1, "Todo 2", "Todo description 2", false));
            todoRepository.save(new Todo(null, 1, "Todo 3", "Todo description 3", false));
            todoRepository.save(new Todo(null, 1, "Todo 4", "Todo description 4", false));
            todoRepository.save(new Todo(null, 1, "Todo 5", "Todo description 5", false));
            todoRepository.save(new Todo(null, 2, "Todo 1", "Todo description 1", false));
            todoRepository.save(new Todo(null, 2, "Todo 2", "Todo description 2", false));
            todoRepository.save(new Todo(null, 2, "Todo 3", "Todo description 3", false));
            todoRepository.save(new Todo(null, 2, "Todo 4", "Todo description 4", false));
            todoRepository.save(new Todo(null, 2, "Todo 5", "Todo description 5", false));
            todoRepository.save(new Todo(null, 3, "Todo 1", "Todo description 1", false));
            todoRepository.save(new Todo(null, 3, "Todo 2", "Todo description 2", false));
            todoRepository.save(new Todo(null, 3, "Todo 3", "Todo description 3", false));
            todoRepository.save(new Todo(null, 3, "Todo 4", "Todo description 4", false));
            todoRepository.save(new Todo(null, 3, "Todo 5", "Todo description 5", false));
            todoRepository.save(new Todo(null, 4, "Todo 1", "Todo description 1", false));
            todoRepository.save(new Todo(null, 4, "Todo 2", "Todo description 2", false));
            todoRepository.save(new Todo(null, 4, "Todo 3", "Todo description 3", false));
            todoRepository.save(new Todo(null, 4, "Todo 4", "Todo description 4", false));
            todoRepository.save(new Todo(null, 4, "Todo 5", "Todo description 5", false));

        };

    }

}
