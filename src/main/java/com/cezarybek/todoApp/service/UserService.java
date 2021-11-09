package com.cezarybek.todoApp.service;

import com.cezarybek.todoApp.DTO.UserDTO;
import com.cezarybek.todoApp.DTO.UserResponse;
import com.cezarybek.todoApp.exception.TodoAppException;
import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.RoleRepository;
import com.cezarybek.todoApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserResponse getAllUsers(int pageNum, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Create pageable instance
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<User> users = userRepository.findAll(pageable);

        //Getting the content for page object
        List<User> listOfUsers = users.getContent();

        //Mapping to output response
        List usersDTO = new ArrayList();
        listOfUsers.forEach(user -> usersDTO.add(new UserDTO(user.getId(), user.getUsername(), user.getEmail())));

        UserResponse userResponse = new UserResponse(usersDTO, users.getNumber(), users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());

        return userResponse;
    }

    public ResponseEntity<UserDTO> addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new TodoAppException(HttpStatus.IM_USED, "Username is already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new TodoAppException(HttpStatus.IM_USED, "Email is already register, please login.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        addRoleToUser(user.getUsername(), "ROLE_USER");

        return new ResponseEntity<>(new UserDTO(user.getId(), user.getUsername(), user.getEmail()), HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteUser(Integer userId) {

        User authUser = getCurrentUser();

        if (authUser.getId() == userId || authUser.getRoles().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN"))) {
            userRepository.findById(userId).orElseThrow(() ->
                    new TodoAppException(HttpStatus.NOT_FOUND, String.format("User with ID %s does not exist!", userId)));
            userRepository.deleteById(userId);
            return new ResponseEntity<>(String.format("User with ID %s deleted successfully", userId), HttpStatus.OK);
        } else {
            throw new TodoAppException(HttpStatus.FORBIDDEN, "You are not allowed to manage this content!");
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

    public ResponseEntity<UserDTO> updateUser(Integer userId, UserDTO user) {

        User authUser = getCurrentUser();

        if (authUser.getId() == userId || authUser.getRoles().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN"))) {
            User userToUpdate = userRepository.findById(userId).orElseThrow(() ->
                    new TodoAppException(HttpStatus.NOT_FOUND, String.format("User with ID %s does not exist!", userId)));

            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());

            userRepository.save(userToUpdate);

            return new ResponseEntity<>(new UserDTO(userId, userToUpdate.getUsername(), userToUpdate.getEmail()), HttpStatus.OK);
        } else {
            throw new TodoAppException(HttpStatus.FORBIDDEN, "You are not allowed to manage this content!");
        }


    }

    private User getCurrentUser() {
        //Retrieving current authenticated user from Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //Loading the User -> id
        User user = userRepository.getByUsername(username);
        return user;
    }
}
