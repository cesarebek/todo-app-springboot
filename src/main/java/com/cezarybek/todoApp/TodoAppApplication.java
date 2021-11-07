package com.cezarybek.todoApp;

import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.service.RoleService;
import com.cezarybek.todoApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            //Adding Roles to database
            roleService.addRole(new Role("ROLE_ADMIN", "Role for administrators of TODO API"));
            roleService.addRole(new Role("ROLE_USER", "Role for standard users of TODO API"));

            //Adding Users to database with default ROLE_USER
            userService.addUser(new User("cesare", "cesarebek1@gmail.com", "pass123"));
            userService.addUser(new User("elisa", "elisa1999@gmail.com", "pass123"));

            //Adding ROLE_ADMIN to Users
            userService.addRoleToUser("cesare", "ROLE_ADMIN");

        };

    }

}
