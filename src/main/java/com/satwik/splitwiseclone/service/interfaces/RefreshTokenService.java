package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;

public interface RefreshTokenService {
    public AuthenticationResponse issueNewToken(RefreshTokenRequest refreshTokenRequest);
}
