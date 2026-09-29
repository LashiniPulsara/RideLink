package com.ridelink.account_service.service;

import com.ridelink.account_service.model.User;
import com.ridelink.account_service.repository.UserRepository;
import com.ridelink.account_service.security.JwtService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

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

        assertEquals(
                "Email already exists",
                exception.getMessage()
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

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

        when(passwordEncoder.matches(
                "Test123",
                "encodedPassword"
        )).thenReturn(true);

        when(jwtService.generateToken(
                "test@gmail.com",
                "CUSTOMER"
        )).thenReturn("test-jwt-token");

        var response =
                userService.login(
                        "test@gmail.com",
                        "Test123"
                );

        assertNotNull(response);
        assertEquals(
                "Login successful",
                response.getMessage()
        );
        assertEquals(
                "test@gmail.com",
                response.getEmail()
        );
        assertEquals(
                "CUSTOMER",
                response.getRole()
        );
        assertEquals(
                "test-jwt-token",
                response.getToken()
        );
    }

    @Test
    void invalidPasswordShouldFailLogin() {

        User user = new User();

        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole("CUSTOMER");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "WrongPassword",
                "encodedPassword"
        )).thenReturn(false);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.login(
                                "test@gmail.com",
                                "WrongPassword"
                        )
                );

        assertEquals(
                "Invalid email or password",
                exception.getMessage()
        );
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

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }
}