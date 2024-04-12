package com.satwik.splitwiseclone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/oauth2/google")
public class OAuthController {

    @Value("${oauth2.google.authUrl}")
    private String authUrl;

    @Value("${oauth2.google.clientId}")
    private String clientId;

    @Value("${oauth2.google.callbackUrl}")
    private String callbackUrl;

    @Value("${oauth2.google.scope}")
    private String scope;

    @Value("${oauth2.google.clientSecret}")
    private String clientSecret;

    @GetMapping("/login")
    public String oauth2Login() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", callbackUrl)
                .queryParam("scope", scope)
                .queryParam("response_type", "code");

        return "redirect:" + builder.toUriString();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code) {
        Map<String, Object> postData = new HashMap<>();
        postData.put("code", code);
        postData.put("client_id", clientId);
        postData.put("client_secret", clientSecret);
        postData.put("redirect_uri", callbackUrl);
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
        ResponseEntity<String> response = restTemplate.exchange(authUrl, HttpMethod.POST, requestEntity, String.class);

        // Print the response
        System.out.println("Response: " + response);
        return response;
    }
}
