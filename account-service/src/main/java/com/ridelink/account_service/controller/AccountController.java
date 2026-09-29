package com.ridelink.account_service.controller;

import com.ridelink.account_service.model.LoginResponse;
import com.ridelink.account_service.model.User;
import com.ridelink.account_service.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Account Controller", description = "Endpoints for user registration, authentication, profile management and account administration")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    // =========================
    // REGISTER
    // =========================
    @Operation(
            summary = "Register a new user",
            description = "Registers a new passenger or driver account. Returns the created user object."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input - required fields missing")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Valid @RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (user.getPhone() == null || user.getPhone().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                userService.register(user)
        );
    }

    // =========================
    // GET ALL USERS (ADMIN ONLY)
    // =========================
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users. This operation is restricted to administrators."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - admin role required")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    // =========================
    // LOGIN
    // =========================
    @Operation(
            summary = "User login",
            description = "Authenticates a user using email and password. Returns a JWT token on success."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful - JWT token returned"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody User loginUser) {

        return ResponseEntity.ok(
                userService.login(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
    }

    // =========================
    // GET USER BY ID
    // =========================
    @Operation(
            summary = "Get user by ID",
            description = "Retrieves the profile of a specific user by their unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "Unique identifier of the user", required = true)
            @PathVariable String id) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }

    // =========================
    // UPDATE PROFILE
    // =========================
    @Operation(
            summary = "Update user profile",
            description = "Updates the name and phone number of an existing user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(
            @Parameter(description = "Unique identifier of the user", required = true)
            @PathVariable String id,
            @RequestBody User user) {

        return ResponseEntity.ok(
                userService.updateProfile(
                        id,
                        user.getName(),
                        user.getPhone()
                )
        );
    }
}