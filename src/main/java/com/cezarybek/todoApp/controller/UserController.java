package com.cezarybek.todoApp.controller;

import com.cezarybek.todoApp.DTO.UserDTO;
import com.cezarybek.todoApp.DTO.UserResponse;
import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cezarybek.todoApp.utils.AppConstants.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public UserResponse getAll(@RequestParam(value = "pageNum", defaultValue = DEFAULT_PAGE_NUM, required = false) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                               @RequestParam(value = "sortBy", defaultValue = DEFAULT_PAGE_SORT_BY, required = false) String sortBy,
                               @RequestParam(value = "sortDir", defaultValue = DEFAULT_PAGE_SORT_DIR, required = false) String sortDir) {
        return userService.getAllUsers(pageNum, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

}
