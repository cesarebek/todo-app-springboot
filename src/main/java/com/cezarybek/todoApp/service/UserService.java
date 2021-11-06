package com.cezarybek.todoApp.service;

import com.cezarybek.todoApp.exception.TodoAppException;
import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.RoleRepository;
import com.cezarybek.todoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new TodoAppException(HttpStatus.IM_USED, "Username is already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new TodoAppException(HttpStatus.IM_USED, "Email is already register, please login.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        addRoleToUser(user.getUsername(), "ROLE_USER");

        return userRepository.save(user);

    }

    public ResponseEntity<String> deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isEmpty()) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(String.format("User with ID %s deleted!", id), HttpStatus.OK);
        } else {
            throw new TodoAppException(HttpStatus.BAD_REQUEST, String.format("User with ID %s not found", id));
        }
    }

    public Optional<User> getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isEmpty()) {
            return user;
        } else {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("User with ID %s not found", id));
        }
    }

    public ResponseEntity<String> addRoleToUser(String user, String role) {
        Optional<User> userName = userRepository.findByUsername(user);
        Role roleName = roleRepository.findByName(role);
        if (user.isEmpty()) {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("User %s not found", user));
        }
        if (role.isEmpty()) {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("Role %s not found", role));
        }
        userName.get().getRoles().add(roleName);
        return new ResponseEntity<>(String.format("Role %s successfully added to %s", role, user), HttpStatus.OK);
    }
}
