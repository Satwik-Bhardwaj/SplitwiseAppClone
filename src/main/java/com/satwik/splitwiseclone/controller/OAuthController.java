package com.satwik.splitwiseclone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/oauth2")
public class OAuthController {

    @GetMapping("/login")
    public String oauth2Login() {

        return "index";
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code) {
        Map<String, Object> postData = new HashMap<>();
        postData.put("code", code);
        postData.put("grant_type", "authorization_code");

        return ResponseEntity.ok(postData.toString());
    }
}
