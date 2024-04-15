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

    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code) {
        Map<String, Object> postData = new HashMap<>();
        postData.put("code", code);
//        postData.put("client_id", clientId);
//        postData.put("client_secret", clientSecret);
//        postData.put("redirect_uri", callbackUrl);
        postData.put("grant_type", "authorization_code");

        // Convert Map to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(postData);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity object containing the request body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send the POST request and get the response
        ResponseEntity<String> response = null; // restTemplate.exchange(authUrl, HttpMethod.POST, requestEntity, String.class);

        // Print the response
        System.out.println("Response: " + response);
        return response;
    }
}
