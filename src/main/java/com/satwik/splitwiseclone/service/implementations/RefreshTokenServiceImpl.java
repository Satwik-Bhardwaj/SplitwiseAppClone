package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;
import com.satwik.splitwiseclone.persistence.models.RefreshToken;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.RefreshTokenRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public RefreshToken createRefreshToken(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()->new RuntimeException("User not found!"));
        Date expirationTime = new Date((20 * 60 * 1000) + new Date(System.currentTimeMillis()).getTime());

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expirationTime(expirationTime) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();

        RefreshToken refreshToken1 = refreshTokenRepository.findByUser(user);
        if(refreshToken1 != null)
            refreshTokenRepository.delete(refreshToken1);

        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    private RefreshToken verifyExpiration(RefreshToken refreshToken) {
        Date expDate = refreshToken.getExpirationTime();
        if(expDate.before(new Date(System.currentTimeMillis()))) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + " - refresh token is expired! Please make a new login...");
        }
        return refreshToken;
    }

    @Override
    public AuthenticationResponse issueNewToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = findByToken(refreshTokenRequest.getRefreshToken());
        refreshToken = verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        String token = jwtUtil.generateToken(String.valueOf(user.getId()));
        refreshTokenRepository.delete(refreshToken);
        refreshToken = createRefreshToken(String.valueOf(user.getId()));
        return new AuthenticationResponse(token, refreshToken.getToken(), "Successfully generated token using refresh token!");
    }
}
