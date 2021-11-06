package com.cezarybek.todoApp.controller;

import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public List<Role> getAll() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Optional<Role> getRole(@PathVariable Integer id) {
        return roleService.getRole(id);
    }

    @PostMapping("/save")
    public Role addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        return roleService.deleteRole(id);
    }

}
