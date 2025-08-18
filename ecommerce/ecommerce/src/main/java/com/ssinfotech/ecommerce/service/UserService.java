package com.ssinfotech.ecommerce.service;

import com.ssinfotech.ecommerce.model.User;
import com.ssinfotech.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // If you use Spring Security for password hashing:
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registration with duplicate email check and password encoding
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign role automatically
        user.setRole("USER");  // hardcoded assignment

        // Save user to DB
        return userRepository.save(user);
    }

    // Login: Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Get user by id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Optionally, update user info
    public User updateUser(Long id, User newData) {
        User user = getUserById(id);
        user.setName(newData.getName());
        user.setPhone(newData.getPhone());
        // ...set any other fields you want users to be able to update
        return userRepository.save(user);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
