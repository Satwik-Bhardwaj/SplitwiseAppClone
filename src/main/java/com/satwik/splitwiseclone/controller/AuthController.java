package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.LoginRequest;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;
import com.satwik.splitwiseclone.service.interfaces.RefreshTokenService;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginRequest.getUserId(), loginRequest.getPassword()
        ));
        String userId = authentication.getName();
        String token = jwtUtil.generateAccessToken(userId);
        String refreshToken = jwtUtil.generateRefreshToken(userId);

        return ResponseEntity.ok(new AuthenticationResponse(token, refreshToken, "Successfully generated token!"));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(refreshTokenService.issueNewToken(refreshTokenRequest));
    }

    @GetMapping("/getUser")
    public String getUser() {
        return "Get the user";
    }

    @GetMapping("/locked")
    public String locked() {
        return "It is locked";
    }
}
