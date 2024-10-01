package org.example.service;

import org.example.model.entities.User;
import org.example.repository.interfaces.UserRepository;

import java.util.List;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id);
        if (user != null) {
            userRepository.delete(user);
        }else {
            System.out.println("User not found");
        }
    }
}
