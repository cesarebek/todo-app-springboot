package com.cezarybek.todoApp.repository;

import com.cezarybek.todoApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User getUserByEmail(String email);
}