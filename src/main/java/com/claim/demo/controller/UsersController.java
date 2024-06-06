package com.claim.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.claim.demo.dto.UserCredentialsDTO;
import com.claim.demo.dto.UserDTO;
import com.claim.demo.entity.User;
import com.claim.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * REST controller for managing users.
 * Provides endpoints for user registration, authentication, and management.
 */
@RestController
@RequestMapping("/users")  // Maps all URLs under '/users' to this controller.
public class UsersController {
    @Autowired
    private UserService userService;  // Service layer handling the business logic for user operations.

    /**
     * Registers a new user.
     * @param user User entity with user details from the request body.
     * @param request HttpServletRequest providing request information for logging or headers.
     * @return ResponseEntity with the registered user and HTTP status 201 (Created).
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user, HttpServletRequest request) {
        UserDTO registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentialsDTO credentials, HttpServletRequest request) {
        // Attempt to authenticate the user with provided username and password
        UserDTO user = userService.loginUser(credentials.getUsername(), credentials.getPassword());
        
        // Check if the user service returned a valid user
        if (user != null) {
            // Create a JWT token with configurations:
            String token = JWT.create()
                .withSubject(user.getUsername())  // Set the 'subject' claim as the username
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // Set expiration time to 10 minutes from now
                .withIssuer(request.getRequestURL().toString())  // Set the 'issuer' claim to the URL where the request was made
                .sign(Algorithm.HMAC256("secret")); // Sign the token with HMAC256 algorithm using the 'secret' key

            // Return the ResponseEntity with status OK, and add the token in the Authorization header
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(user);
        } else {
            // If the user is not found or the authentication failed, return an unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    /**
     * Retrieves a user by their ID.
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing the user DTO.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
    	UserDTO user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Updates user details.
     * @param userId The ID of the user to update.
     * @param updatedUser User entity with updated user details from the request body.
     * @return ResponseEntity with the updated user DTO.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
    	UserDTO user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    /**
     * Deletes a user by their ID.
     * @param userId The ID of the user to delete.
     * @return ResponseEntity with HTTP status 204 (No Content) indicating successful deletion.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
