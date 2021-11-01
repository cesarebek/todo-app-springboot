package com.cezarybek.todoApp.service;

import com.cezarybek.todoApp.model.User;
import com.cezarybek.todoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public String deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isEmpty()){
            userRepository.deleteById(id);
            return "User with id " + id + " deleted!";
        }else{
            return "User with id " + id + " not exist!";
        }
    }

    public Optional<User> getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isEmpty()){
            return user;
        }else{
            throw new NullPointerException();
        }

    }
}
