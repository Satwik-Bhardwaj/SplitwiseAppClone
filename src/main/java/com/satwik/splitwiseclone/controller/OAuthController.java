package com.satwik.splitwiseclone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String TOKEN_URI;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String REDIRECT_URI;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String oauth2Login() {

        return "index";
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        // TODO : Match state

        Map<String, Object> postData = new HashMap<>();
        postData.put("code", code);
        postData.put("client_id", CLIENT_ID);
        postData.put("client_secret", CLIENT_SECRET);
        postData.put("redirect_uri", REDIRECT_URI);
        postData.put("grant_type", "authorization_code");

        // Convert Map to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(postData);
        } catch (Exception e) {
            System.out.println("Error converting map to JSON: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity object containing the request body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, requestEntity, String.class);

        // decoding token
        String json_response = response.getBody();
        ObjectMapper objectMapper1 = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper1.readTree(json_response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String idToken = jsonNode.get("id_token").asText();
        Claims userClaims = jwtUtil.getClaimsOfOAuth2Token(idToken, CLIENT_SECRET);
        String user = userClaims.getSubject();
        return ResponseEntity.ok(user);
    }
}
