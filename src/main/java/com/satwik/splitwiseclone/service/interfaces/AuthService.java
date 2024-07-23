package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.LoginRequest;
import com.satwik.splitwiseclone.persistence.dto.user.RefreshTokenRequest;

public interface AuthService {
    public AuthenticationResponse authenticateUser(LoginRequest loginRequest);
    public AuthenticationResponse issueNewToken(RefreshTokenRequest refreshTokenRequest);
}
