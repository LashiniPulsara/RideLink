package com.ridelink.account_service.controller;

import com.ridelink.account_service.model.LoginResponse;
import com.ridelink.account_service.model.User;
import com.ridelink.account_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    // =========================
    // REGISTER
    // =========================
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
    // GET ALL USERS
    // ADMIN ONLY
    // =========================
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    // =========================
    // LOGIN
    // =========================
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
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }

    // =========================
    // UPDATE PROFILE
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(
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