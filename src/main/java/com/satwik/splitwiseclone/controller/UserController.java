package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.UserDTO;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    // register user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(registerUserRequest));

    }

    // get the user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int userId) {
        // TODO : exception might can occur here for status
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findUserById(userId));
    }

    // update the user details
    @PutMapping("update/{userId}")
    public ResponseEntity<String> updateUser(@RequestBody RegisterUserRequest updateUserRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(updateUserRequest));

    }

}
