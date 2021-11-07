package com.cezarybek.todoApp.service;

import com.cezarybek.todoApp.DTO.UserDTO;
import com.cezarybek.todoApp.exception.TodoAppException;
import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.RoleRepository;
import com.cezarybek.todoApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List usersDTO = new ArrayList();
        users.forEach(user -> usersDTO.add(new UserDTO(user.getId(), user.getUsername(), user.getEmail())));
        return usersDTO;
    }

    public ResponseEntity<UserDTO> addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new TodoAppException(HttpStatus.IM_USED, "Username is already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new TodoAppException(HttpStatus.IM_USED, "Email is already register, please login.");
        }

        log.info(user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        addRoleToUser(user.getUsername(), "ROLE_USER");

        return new ResponseEntity<>(new UserDTO(user.getId(), user.getUsername(), user.getEmail()), HttpStatus.CREATED);
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

    public UserDTO getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isEmpty()) {
            User account = user.get();
            return new UserDTO(account.getId(), account.getUsername(), account.getEmail());
        } else {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("User with ID %s not found", id));
        }
    }

    public ResponseEntity<String> addRoleToUser(String userName, String roleName) {
        Optional<User> user = userRepository.findByUsername(userName);
        Role role = roleRepository.findByName(roleName);
        if (user.isEmpty()) {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("User %s not found", userName));
        }
        if (role == null) {
            throw new TodoAppException(HttpStatus.NOT_FOUND, String.format("Role %s not found", roleName));
        }

        user.get().getRoles().add(role);

        return new ResponseEntity<>(String.format("Role %s successfully added to %s", role, user), HttpStatus.OK);
    }
}
