package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import java.util.UUID;

public interface UserService {
    String saveUser(RegisterUserRequest registerUserRequest);

    UserDTO findUserById(UUID userId) throws Exception;

    String deleteUser(UUID userId) throws Exception;

    String updateUser(UUID userId, RegisterUserRequest registerUserRequest) throws Exception;

    // TODO : create a specific response for the new user when stored

    // TODO : add more code for user security

}
