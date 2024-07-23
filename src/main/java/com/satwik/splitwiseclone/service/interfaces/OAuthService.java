package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;

public interface OAuthService {
    public AuthenticationResponse handleCallback(String code, String state);
}
