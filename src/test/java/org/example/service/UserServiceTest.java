package org.example.service;

import org.example.exception.UserNotFoundException;
import org.example.model.entities.User;
import org.example.model.enums.UserRole;
import org.example.repository.interfaces.TokenRepository;
import org.example.repository.interfaces.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    TokenService tokenService;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void UserService_getUserById_returnsUser() {

        //Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("Password123");
        user.setRole(UserRole.USER);

        //When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(userId);

        //Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void UserService_getUserById_throwsUserNotFoundException() {

        //Given
        Long userId = 1L;

        // When & Then
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(userId));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    void UserService_getUserById_throwsIllegalArgumentException() {

        //Given
        Long userId = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserById(null));

        verify(userRepository, never()).findById(userId);
    }

    @Test
    void UserService_getAllUsers_returnsAllUsers() {

        //Given
        User user1 = new User("firstName1","lastName1","user1@example.com","Password123",UserRole.USER);
        User user2 = new User("firstName2","lastName2","user2@example.com","Password123",UserRole.USER);
        User user3 = new User("firstName3","lastName3","user3@example.com","Password123",UserRole.MANAGER);
        User user4 = new User("firstName4","lastName4","user4@example.com","Password123",UserRole.USER);

        List<User> userList = List.of(user1,user2,user3,user4);

        //When
        when(userRepository.findAll()).thenReturn(userList);
        List<User> resultedList = userService.getAllUsers();

        //Then
        assertEquals(4, resultedList.size());
        verify(userRepository).findAll();
    }

    @Test
    void UserService_getAllUsers_returnsEmptyList() {

        //Given
        List<User> userList = List.of();

        //When
        when(userRepository.findAll()).thenReturn(userList);
        List<User> resultedList = userService.getAllUsers();

        //Then
        assertEquals(0, resultedList.size());
        verify(userRepository).findAll();
    }



    }