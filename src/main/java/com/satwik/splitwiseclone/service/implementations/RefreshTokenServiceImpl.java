package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;
import com.satwik.splitwiseclone.service.interfaces.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public AuthenticationResponse issueNewToken(RefreshTokenRequest refreshTokenRequest) {

        if(refreshTokenRequest == null || refreshTokenRequest.getRefreshToken() == null)
            throw new RuntimeException("Request is invalid");

        String accessToken = jwtUtil.generateAccessTokenFromRefresh(refreshTokenRequest.getRefreshToken());
        String userId = jwtUtil.getUserId(accessToken);
        String refreshToken = jwtUtil.generateRefreshToken(userId);
        return new AuthenticationResponse(accessToken, refreshToken, "Successfully generated token from refresh!");
    }
}
