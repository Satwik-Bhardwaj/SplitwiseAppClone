package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.LoginRequest;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        log.info("Post Endpoint: login user with request: {}", loginRequest);
        AuthenticationResponse response = authService.authenticateUser(loginRequest);
        log.info("Post Endpoint: login user with response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Post Endpoint: refresh token generation with request: {}", refreshTokenRequest);
        AuthenticationResponse response = authService.issueNewToken(refreshTokenRequest);
        log.info("Post Endpoint: refresh token generation with response: {}", response);
        return ResponseEntity.ok(response);
    }
}