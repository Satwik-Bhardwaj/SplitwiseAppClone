package com.satwik.splitwiseclone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satwik.splitwiseclone.persistence.dto.user.PhoneDTO;
import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetUser() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("1");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("satwik");
        userDTO.setEmail("satwikbhardwaj@gmail.com");
        when(userService.findUserById(1)).thenReturn(userDTO);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("satwik"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("");
        registerUserRequest.setPassword("testPassword");
        registerUserRequest.setPhone(new PhoneDTO("+91", 45_34_23_12_23L));
        registerUserRequest.setEmail("satwik12@gmail.com");

        when(userService.saveUser(registerUserRequest)).thenReturn("User registered successfully");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerUserRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("1");

        RegisterUserRequest updateUserRequest = new RegisterUserRequest();
        updateUserRequest.setUsername(null);
        updateUserRequest.setPassword("itsnew");
        when(userService.updateUser(1, updateUserRequest)).thenReturn("User updated successfully");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(put("api/v1/user/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully"));
    }

//    @WithMockUser(username = "1", roles = "USER")
//    public void testUpdateUser() throws Exception {
//        // Mocking authentication behavior
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        when(authentication.getName()).thenReturn("1");
//
//        RegisterUserRequest updateUserRequest = new RegisterUserRequest();
//        updateUserRequest.setUsername("updatedUsername");
//        updateUserRequest.setPassword("updatedPassword");
//
//        UserController userController = new UserController();
//        userController.setUserService(userService);
//
//        when(userService.updateUser(1, updateUserRequest)).thenReturn("User details updated successfully");
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//
//        mockMvc.perform(put("/api/v1/user/update")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(updateUserRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("User details updated successfully"));
//    }
//
//    @Test
//    @WithMockUser(username = "1", roles = "USER")
//    public void testDeleteUser() throws Exception {
//        // Mocking authentication behavior
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        when(authentication.getName()).thenReturn("1");
//
//        UserController userController = new UserController();
//        userController.setUserService(userService);
//
//        when(userService.deleteUser(1)).thenReturn("User deleted successfully");
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//
//        mockMvc.perform(delete("/api/v1/user/delete"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("User deleted successfully"));
//    }
//}
}