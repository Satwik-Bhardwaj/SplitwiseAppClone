package com.satwik.splitwiseclone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satwik.splitwiseclone.configuration.jwt.JwtUtil;
import com.satwik.splitwiseclone.persistence.dto.user.LoginRequest;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;

    private ObjectMapper objectMapper;

    @InjectMocks
    private JwtUtil jwtUtil;

    @InjectMocks
    AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testLoginUser() throws Exception {
        // mock stuff
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId("1");
        loginRequest.setPassword("satwik123");
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        // defining behaviours
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(authentication);
        when(userDetails.getUsername()).thenReturn(String.valueOf(loginRequest.getUserId()));
        when(jwtUtil.generateToken(String.valueOf(loginRequest.getUserId()))).thenReturn("generatedToken");

//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("generatedToken"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Successfully generated token!"));
    }

}
