package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;
import com.satwik.splitwiseclone.persistence.models.RefreshToken;

public interface RefreshTokenService {
    // create refresh token when login and refreshing
    public RefreshToken createRefreshToken(String username);

    public AuthenticationResponse issueNewToken(RefreshTokenRequest refreshTokenRequest);
}
