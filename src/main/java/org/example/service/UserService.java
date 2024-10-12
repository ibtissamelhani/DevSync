package org.example.service;

import org.example.exception.UserAlreadyExistException;
import org.example.exception.UserNotFoundException;
import org.example.model.entities.User;
import org.example.repository.interfaces.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        Optional<User> optionalUser = getUserById(user.getId());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException("User with id " + user.getId() + " already exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        Optional<User> optionalUser = getUserById(user.getId());
        if (optionalUser.isPresent()) {
            return userRepository.update(user);
        }else {
            throw new UserNotFoundException("User with id " + user.getId() + " not found");
        }
    }

    public Boolean deleteUser(Long id) {
        Optional<User> optionalUser = getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return userRepository.delete(user);
        }else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }
}
