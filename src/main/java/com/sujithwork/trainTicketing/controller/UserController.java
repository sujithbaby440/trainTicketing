package com.sujithwork.trainTicketing.controller;

import com.sujithwork.trainTicketing.entity.Users;
import com.sujithwork.trainTicketing.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public Users registerUser(@RequestBody Users user) {
        return userService.saveUser(user);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Fetch a user")
    public Users getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/remove/{userId}")
    @Operation(summary = "Delete a user")
    public void removeUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/modify-user")
    @Operation(summary = "Update a user")
    public Users modifyUserSeat(@RequestBody Users user) {
        return userService.updateUser(user);
    }
}
