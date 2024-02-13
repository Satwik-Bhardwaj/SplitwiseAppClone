package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.UserDTO;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.UserRepository;

import java.util.List;

public interface UserService {

    // TODO : create a specific response for the new user when stored

    // save & update
    String saveUser(RegisterUserRequest request);

    // find by id
    UserDTO findUserById(int userId);


    // delete by id
    String deleteUser(int userId);

    // TODO : add more code for user security

}
