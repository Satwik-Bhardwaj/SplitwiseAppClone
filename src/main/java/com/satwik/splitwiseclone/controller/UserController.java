package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    // register user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(registerUserRequest));

    }

    // get the user
    @GetMapping("")
    public ResponseEntity<UserDTO> getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // update the user details
    @PutMapping("update")
    public ResponseEntity<String> updateUser(@RequestBody RegisterUserRequest updateUserRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(userId, updateUserRequest));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // delete the user
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        try {
            return ResponseEntity.ok(userService.deleteUser(userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
