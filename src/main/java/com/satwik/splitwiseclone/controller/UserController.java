package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
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
    public ResponseEntity<String> updateUser(@RequestBody RegisterUserRequest updateUserRequest, @PathVariable int userId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(userId, updateUserRequest));

    }

    // delete the user
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {

        return ResponseEntity.ok(userService.deleteUser(userId));

    }

}
