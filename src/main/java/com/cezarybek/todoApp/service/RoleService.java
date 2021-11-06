package com.cezarybek.todoApp.service;

import com.cezarybek.todoApp.exception.TodoAppException;
import com.cezarybek.todoApp.model.Role;
import com.cezarybek.todoApp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRole(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isEmpty()) {
            return role;
        } else {
            throw new TodoAppException(HttpStatus.BAD_REQUEST, String.format("Role with ID %s not found", id));
        }
    }

    public Role addRole(Role role) {
        try {
            return roleRepository.save(role);
        } catch (Exception e) {
            throw new TodoAppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public ResponseEntity<String> deleteRole(Integer id) {
        try {
            roleRepository.deleteById(id);
            return new ResponseEntity<>(String.format("Role with ID %s deleted successfully!", id), HttpStatus.OK);
        } catch (Exception e) {
            throw new TodoAppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
