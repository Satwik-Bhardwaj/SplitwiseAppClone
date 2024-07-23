package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.service.interfaces.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/oauth2")
public class OAuthController {

    @Autowired
    OAuthService oAuthService;

    @GetMapping("/callback")
    public ResponseEntity<AuthenticationResponse> handleCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        // TODO : Match state
        log.info("Get Endpoint: oAuth callback with code : {}, state : {}", code, state);
        AuthenticationResponse response = oAuthService.handleCallback(code, state);
        log.info("Get Endpoint: callback response: {}", response);
        return ResponseEntity.ok(response);
    }
}
