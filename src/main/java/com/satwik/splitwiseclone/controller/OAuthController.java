package com.satwik.splitwiseclone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.constants.enums.RegistrationMethod;
import com.satwik.splitwiseclone.persistence.dto.user.AuthenticationResponse;
import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String oauth2Login() {

        return "index";
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthenticationResponse> handleCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
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

        // getting token
        String json_response = response.getBody();
        ObjectMapper objectMapper1 = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper1.readTree(json_response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String id_token = jsonNode.get("id_token").asText();


        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(id_token);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        assert idToken != null;
        GoogleIdToken.Payload payload = idToken.getPayload();

        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);

        // Get profile information from payload
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        // check if user already exists (using email)
        // TODO : use more details from token to check the user exists
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) {
            User newUser = new User();
            newUser.setUsername(UUID.randomUUID().toString());
            newUser.setEmail(email);
//            newUser.setCountryCode(request.getPhone().getCountryCode());
//            newUser.setPhoneNumber(request.getPhone().getPhoneNumber());
            newUser.setRegistrationMethod(RegistrationMethod.GOOGLE);
            newUser = userRepository.save(newUser);

            Group group = new Group();
            group.setGroupName("Non Grouped Expenses");
            group.setUser(newUser);
            group.setDefaultGroup(true);
            groupRepository.save(group);

            String token = jwtUtil.generateAccessToken(newUser.getId().toString());
            String refreshToken = jwtUtil.generateRefreshToken(newUser.getId().toString());

            return ResponseEntity.ok(new AuthenticationResponse(token, refreshToken, "New token issue for google user."));
        } else {
            String token = jwtUtil.generateAccessToken(user.get().getId().toString());
            String refreshToken = jwtUtil.generateRefreshToken(user.get().getId().toString());

            return ResponseEntity.ok(new AuthenticationResponse(token, refreshToken, "New token issue for google user."));
        }
    }
}
