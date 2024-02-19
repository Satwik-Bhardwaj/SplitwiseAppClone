package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;

import java.nio.file.AccessDeniedException;

public interface UserService {
    String saveUser(RegisterUserRequest registerUserRequest);

    UserDTO findUserById(int userId) throws Exception;

    String deleteUser(int userId) throws Exception;

    String updateUser(int userId, RegisterUserRequest registerUserRequest) throws Exception;

    // TODO : create a specific response for the new user when stored

//    // save & update
//    String saveUser(RegisterUserRequest request);
//
//    // find by id
//    UserDTO findUserById(int userId);
//
//
//    // delete by id
//    String deleteUser(int userId);

    // TODO : add more code for user security

}
