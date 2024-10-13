package org.example.service;

import org.example.exception.UserNotFoundException;
import org.example.model.entities.Token;
import org.example.model.entities.User;
import org.example.model.enums.TokenType;
import org.example.model.enums.UserRole;
import org.example.repository.interfaces.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    UserRepository userRepository;
    TokenService tokenService;

    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public void createUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        Token modificationToken = new Token(TokenType.MODIFICATION, LocalDate.now().plusDays(1),savedUser,2);
        Token suppressionToken = new Token(TokenType.SUPPRESSION, LocalDate.now().plusMonths(1),savedUser,1);
        tokenService.save(modificationToken);
        tokenService.save(suppressionToken);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long userId, String firstName, String lastName, String email, String role) {

        Optional<User> optionalUser = this.getUserById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setRole(UserRole.valueOf(role));
            userRepository.update(user);
        } else {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }

    public Boolean deleteUser(Long id) {
        Optional<User> user = getUserById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            return userRepository.delete(user1);
        }else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public List<User> getRegularUsers() {
        List<User> allUsers = getAllUsers();
        return allUsers.stream()
                .filter(user -> user.getRole().equals(UserRole.USER))
                .collect(Collectors.toList());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
