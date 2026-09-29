package com.ridelink.account_service.service;

import com.ridelink.account_service.model.User;
import com.ridelink.account_service.repository.UserRepository;
import com.ridelink.account_service.security.JwtService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUpAuthentication() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    // ==========================================
    // REGISTER TESTS
    // ==========================================

    @Test
    void registerUserSuccessfully() {

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPassword("Test123");
        user.setPhone("0711111111");

        when(userRepository.existsByEmail("test@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("Test123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.register(user);

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals("0711111111", result.getPhone());
        assertEquals("CUSTOMER", result.getRole());
        assertEquals("encodedPassword", result.getPassword());

        verify(userRepository).save(user);
        verify(passwordEncoder).encode("Test123");
    }

    @Test
    void registerDuplicateEmailShouldFail() {

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPassword("Test123");
        user.setPhone("0711111111");

        when(userRepository.existsByEmail("test@gmail.com"))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.register(user)
                );

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    // ==========================================
    // LOGIN TESTS
    // ==========================================

    @Test
    void loginSuccessfully() {

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");
        user.setPhone("0711111111");
        user.setRole("CUSTOMER");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("Test123", "encodedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken("test@gmail.com", "CUSTOMER"))
                .thenReturn("test-jwt-token");

        var response = userService.login("test@gmail.com", "Test123");

        assertNotNull(response);
        assertEquals("Login successful", response.getMessage());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
        assertEquals("test-jwt-token", response.getToken());
    }

    @Test
    void invalidPasswordShouldFailLogin() {

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole("CUSTOMER");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("WrongPassword", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.login("test@gmail.com", "WrongPassword")
                );

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void loginShouldFailWhenUserNotFound() {

        when(userRepository.findByEmail("unknown@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.login("unknown@gmail.com", "Test123")
                );

        assertEquals("Invalid email or password", exception.getMessage());
    }

    // ==========================================
    // GET USER BY ID TESTS
    // ==========================================

    @Test
    void getUserByIdSuccessfully() {

        User user = new User();
        user.setId("user-123");
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPhone("0711111111");
        user.setRole("CUSTOMER");

        when(userRepository.findById("user-123"))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById("user-123");

        assertNotNull(result);
        assertEquals("user-123", result.getId());
        assertEquals("Test User", result.getName());
        assertEquals("test@gmail.com", result.getEmail());

        verify(userRepository).findById("user-123");
    }

    @Test
    void getUserByIdShouldFailWhenUserNotFound() {

        when(userRepository.findById("invalid-id"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.getUserById("invalid-id")
                );

        assertEquals("User not found", exception.getMessage());
    }

    // ==========================================
    // UPDATE PROFILE TESTS
    // ==========================================

    @Test
    void updateProfileSuccessfully() {

        User existingUser = new User();
        existingUser.setId("user-123");
        existingUser.setName("Old Name");
        existingUser.setEmail("test@gmail.com");
        existingUser.setPhone("0711111111");
        existingUser.setRole("CUSTOMER");

        when(userRepository.findById("user-123"))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateProfile(
                "user-123",
                "New Name",
                "0722222222"
        );

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("0722222222", result.getPhone());
        assertEquals("test@gmail.com", result.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateProfileShouldFailWhenUserNotFound() {

        when(userRepository.findById("invalid-id"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.updateProfile(
                                "invalid-id",
                                "New Name",
                                "0722222222"
                        )
                );

        assertEquals("User not found", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    // ==========================================
    // GET ALL USERS TESTS
    // ==========================================

    @Test
    void getAllUsersSuccessfully() {

        User user1 = new User();
        user1.setId("user-1");
        user1.setName("User One");
        user1.setEmail("user1@gmail.com");

        User user2 = new User();
        user2.setId("user-2");
        user2.setName("User Two");
        user2.setEmail("user2@gmail.com");

        when(userRepository.findAll())
                .thenReturn(java.util.List.of(user1, user2));

        var result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("User One", result.get(0).getName());
        assertEquals("User Two", result.get(1).getName());

        verify(userRepository).findAll();
    }
}