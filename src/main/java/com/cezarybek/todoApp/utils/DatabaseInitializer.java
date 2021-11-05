package com.cezarybek.todoApp.utils;

import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.RoleRepository;
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
    private RoleRepository roleRepository;

    public void run(ApplicationArguments args) {
        //Adding Users to database
        userService.addUser(new User("cesare", "cesarebek1@gmail.com", "pass123", new ArrayList<>()));
        userService.addUser(new User("elisa", "elisa1999@gmail.com", "pass123", new ArrayList<>()));


        //Adding Roles to database
        roleRepository.save(new Role("ROLE_ADMIN", "Role for administrators of TODO API"));
        roleRepository.save(new Role("ROLE_USER", "Role for standard users of TODO API"));


        //Adding Roles to Users

        //Adding Todos


    }
}
