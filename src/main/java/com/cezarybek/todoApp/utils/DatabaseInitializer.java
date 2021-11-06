package com.cezarybek.todoApp.utils;

import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.service.RoleService;
import com.cezarybek.todoApp.service.TodoService;
import com.cezarybek.todoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DatabaseInitializer implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private RoleService roleService;

    public void run(ApplicationArguments args) {
        //Adding Roles to database
        roleService.addRole(new Role("ROLE_ADMIN", "Role for administrators of TODO API"));
        roleService.addRole(new Role("ROLE_USER", "Role for standard users of TODO API"));

        //Adding Users to database
        userService.addUser(new User("cesare", "cesarebek1@gmail.com", "pass123", new ArrayList<>()));
        userService.addUser(new User("elisa", "elisa1999@gmail.com", "pass123", new ArrayList<>()));

        //Adding Roles to Users
        userService.addRoleToUser("cesare", "ROLE_ADMIN");
        userService.addRoleToUser("cesare", "ROLE_USER");
        userService.addRoleToUser("elisa", "ROLE_USER");
    }
}
