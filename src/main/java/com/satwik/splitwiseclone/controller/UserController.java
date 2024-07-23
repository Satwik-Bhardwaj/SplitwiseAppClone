package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.configuration.security.LoggedInUser;
import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoggedInUser loggedInUser;

    // register user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        log.info("Post Endpoint: register user with request: {}", registerUserRequest);
        String response = userService.saveUser(registerUserRequest);
        log.info("Post Endpoint: register user with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // get the user
    @GetMapping("")
    public ResponseEntity<UserDTO> getUser() {
        log.info("Get Endpoint: get user");
        UserDTO response = userService.findUser();
        log.info("Get Endpoint: get user with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // update the user details
    @PutMapping("update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable UUID userId, @Valid @RequestBody RegisterUserRequest updateUserRequest) {
        log.info("Put Endpoint: update user with id: {}, and register user request: {}", userId, updateUserRequest);
        String response = userService.updateUser(userId, updateUserRequest);
        log.info("Put Endpoint: update user with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // delete the user
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser() {
        log.info("Delete Endpoint: delete user");
        String response = userService.deleteUser();
        log.info("Delete Endpoint: delete user with response: {}", response);
        return ResponseEntity.ok(response);
    }

}
