package com.example.googledrive.controller;

import com.example.googledrive.dto.CreateUserDto;
import com.example.googledrive.dto.UpdateUserDto;
import com.example.googledrive.entity.User;
import com.example.googledrive.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing user-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting users.
 *
 * The class is annotated with Spring's RestController, indicating that it handles RESTful API requests.
 * Each method is documented to describe its purpose and functionality.
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param dto The CreateUserDto containing user details for creation.
     * @return The created user and HTTP status code 201 (CREATED) upon success.
     */
    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDto dto) {
        User user = userService.createUser(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Endpoint for retrieving a list of all users.
     *
     * @return A list of users and HTTP status code 200 (OK) upon success.
     */
    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The requested user and HTTP status code 200 (OK) upon success.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * Endpoint for updating a user by their ID.
     *
     * @param id  The ID of the user to update.
     * @param dto The UpdateUserDto containing user details for updating.
     * @return The updated user and HTTP status code 200 (OK) upon success.
     */
    @PutMapping("/user/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserDto dto) {
        User user = userService.updateUser(id, dto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Endpoint for deleting a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return HTTP status code 204 (NO CONTENT) upon successful deletion.
     */
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
