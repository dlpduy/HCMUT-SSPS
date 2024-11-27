package com.project.SSPS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.SSPS.model.User;
import com.project.SSPS.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public User handleUpdateUser(User user) {
        return this.userRepository.save(user);
    }

    public User getUserbyUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    public User getUserbyUsernameAndCodeVerify(String username, String codeVerify) {
        return this.userRepository.findByUsernameAndCodeVerify(username, codeVerify).orElse(null);
    }

    public boolean checkUserExists(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}
