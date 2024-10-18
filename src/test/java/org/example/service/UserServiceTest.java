package org.example.service;

import jakarta.jws.soap.SOAPBinding;
import org.example.exception.UserNotFoundException;
import org.example.model.entities.Token;
import org.example.model.entities.User;
import org.example.model.enums.UserRole;
import org.example.repository.interfaces.TokenRepository;
import org.example.repository.interfaces.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
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
    void UserService_getUserById_throwsUserNotFoundException_WhenUserNotFound() {

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
    void UserService_getUserById_throwsIllegalArgumentException_WhenIdIsNull() {

        //Given
        Long userId = null;

        // When & Then
        assertThrows(IllegalArgumentException.class,
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

    @Test
    void UserService_getUserByEmail_returnsUser() {

        //Give
        String email = "john.doe@example.com";
        User user = User.builder()
                .email(email)
                .firstName("John")
                .lastName("Doe")
                .password("Password123")
                .role(UserRole.USER)
                .build();

        //When
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User result = userService.getUserByEmail(email);

        //Then
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void UserService_getUserByEmail_throwsUserNotFoundException_WhenUserNotFound() {

        //Given
        String nonExistingEmail = "john.doe@example.com";

        //When & Then
        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> userService.getUserByEmail(nonExistingEmail));
        verify(userRepository).findByEmail(nonExistingEmail);
    }

    @Test
    void UserService_getUserByEmail_throwsIllegalArgumentException_WhenEmailIsNull() {

        //Given
        String illegalArgument = null;

        //When & Then
        assertThrows(IllegalArgumentException.class,
                () -> userService.getUserByEmail(illegalArgument));

        verify(userRepository,never()).findByEmail(illegalArgument);
    }

    @Test
    void UserService_getRegularUsers_returnsAllUsersWithRoleUser() {

        //Given
        User user1 = new User("firstName1","lastName1","user1@example.com","Password123",UserRole.USER);
        User user2 = new User("firstName2","lastName2","user2@example.com","Password123",UserRole.USER);
        User user3 = new User("firstName3","lastName3","user3@example.com","Password123",UserRole.MANAGER);
        User user4 = new User("firstName4","lastName4","user4@example.com","Password123",UserRole.USER);

        List<User> allUsers = List.of(user1,user2,user3,user4);

        List<User> expectedList = List.of(user1,user2,user4);

        //When
        when(userRepository.findAll()).thenReturn(allUsers);
        List<User> resultedList = userService.getRegularUsers();

        //Then
        assertEquals(3, resultedList.size());
        assertEquals(expectedList, resultedList);
        verify(userRepository).findAll();
    }

    @Test
    void UserService_getRegularUsers_returnsEmptyList() {

        //Given
        User user1 = new User("firstName1","lastName1","user1@example.com","Password123",UserRole.MANAGER);
        User user2 = new User("firstName2","lastName2","user2@example.com","Password123",UserRole.MANAGER);
        User user3 = new User("firstName3","lastName3","user3@example.com","Password123",UserRole.MANAGER);
        User user4 = new User("firstName4","lastName4","user4@example.com","Password123",UserRole.MANAGER);

        List<User> allUsers = List.of(user1,user2,user3,user4);

        //When
        when(userRepository.findAll()).thenReturn(allUsers);
        List<User> resultedList = userService.getRegularUsers();

        //Then
        assertEquals(0, resultedList.size());
        verify(userRepository).findAll();
    }

    @Test
    void UserService_createUser_succeed() {

        //Given
        String email = "john.doe@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "Password123";

        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                .role(UserRole.USER)
                .build();

        User savedUser = User.builder()
                .id(1L)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                .role(UserRole.USER)
                .build();

        //When
        when(userRepository.save(user)).thenReturn(savedUser);
        User result = userService.createUser(user);

        //Then
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertTrue(BCrypt.checkpw(password, result.getPassword()));
        verify(userRepository).save(user);
        verify(userRepository).findByEmail(email);
        verify(tokenService, times(2)).save(any(Token.class));
    }

    @Test
    void UserService_createUser_throwsException_WhenUserSaveFails() {

        //Given
        User user = User.builder()
                .email("email@example.com")
                .firstName("firstName")
                .lastName("lastName")
                .password(BCrypt.hashpw("password", BCrypt.gensalt()))
                .role(UserRole.USER)
                .build();
        //When & Then
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class,
                ()->userService.createUser(user));

        verify(tokenService,never()).save(any(Token.class));

    }

    @Test
    void UserService_createUser_throwsException_WhenEmailAlreadyExists() {

        // Given
        String email = "email@example.com";
        User user = User.builder()
                .email(email)
                .firstName("firstName")
                .lastName("lastName")
                .password(BCrypt.hashpw("password", BCrypt.gensalt()))
                .role(UserRole.USER)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });
        verify(userRepository, never()).save(any(User.class));
        verify(tokenService, never()).save(any(Token.class));
    }


    @Test
    void UserService_deleteUser_succeed() {

        //Given
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .email("email@example.com")
                .firstName("firstName")
                .lastName("lastName")
                .password(BCrypt.hashpw("password", BCrypt.gensalt()))
                .role(UserRole.USER)
                .build();

        //When
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.delete(user)).thenReturn(true);
        boolean result = userService.deleteUser(id);

        //Then
        verify(userRepository).findById(id);
        verify(userRepository).delete(user);
        assertTrue(result);
    }

    @Test
    void UserService_deleteUser_throwsIllegalArgumentException_WhenIdIsNull() {

        //Given
        Long id = null;

        //When & Then
        assertThrows(IllegalArgumentException.class,
                ()->userService.deleteUser(id));

        verify(userRepository,never()).findById(id);
        verify(userRepository,never()).delete(any(User.class));
    }

    @Test
    void UserService_deleteUser_throwsUserNotFoundException_WhenIdDoesNotExist() {

        //Given
        Long id = 2L;

        //When & Then
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                ()->userService.deleteUser(id));

        verify(userRepository).findById(id);
        verify(userRepository,never()).delete(any(User.class));
    }

    @Test
    void UserService_updateUser_succeeds() {

        // Given
        Long userId = 1L;
        User existingUser = User.builder()
                .id(userId)
                .firstName("OldFirstName")
                .lastName("OldLastName")
                .email("oldemail@example.com")
                .role(UserRole.USER)
                .build();

        String newFirstName = "NewFirstName";
        String newLastName = "NewLastName";
        String newEmail = "newemail@example.com";
        String newRole = "MANAGER";

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        userService.updateUser(userId, newFirstName, newLastName, newEmail, newRole);

        // Then
        assertEquals(newFirstName, existingUser.getFirstName());
        assertEquals(newLastName, existingUser.getLastName());
        assertEquals(newEmail, existingUser.getEmail());
        assertEquals(UserRole.MANAGER, existingUser.getRole());
        verify(userRepository).findById(userId);
        verify(userRepository).update(existingUser);
    }

    @Test
    void UserService_updateUser_throwsUserNotFoundException_whenUserNotFound() {

        // Given
        Long userId = 1L;

        String newFirstName = "NewFirstName";
        String newLastName = "NewLastName";
        String newEmail = "newemail@example.com";
        String newRole = "ADMIN";

        // When & Then
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(userId, newFirstName, newLastName, newEmail, newRole));
        verify(userRepository).findById(userId);
        verify(userRepository, never()).update(any());
    }




}