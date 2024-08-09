package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.exception.BadRequestException;
import com.satwik.splitwiseclone.exception.DataNotFoundException;
import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.LoginRequest;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;
import com.satwik.splitwiseclone.persistence.entities.User;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getUserEmail()).orElseThrow(() -> new DataNotFoundException("User not found."));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid User mail or password!");
        }
        String token = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return new AuthenticationResponse(token, refreshToken, "Successfully generated token!");
    }

    @Override
    public AuthenticationResponse issueNewToken(RefreshTokenRequest refreshTokenRequest) {
        String userId = jwtUtil.getClaimsOfRefreshToken(refreshTokenRequest.getRefreshToken()).getSubject();
        User user = userRepository.findByEmail(userId).orElseThrow(() -> new DataNotFoundException("User not found."));
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return new AuthenticationResponse(accessToken, refreshToken, "Successfully generated token from refresh!");
    }
}
