package com.example.reviewapp.service;

import com.example.reviewapp.entity.User;
import com.example.reviewapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public boolean authenticate(String name, String password) {
        Optional<User> userOpt = userRepository.findByName(name);
        if (userOpt.isPresent()) {
            return userOpt.get().getPassword().equals(password);
        }
        return false;
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
