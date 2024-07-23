package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import java.util.UUID;

public interface UserService {
    String saveUser(RegisterUserRequest registerUserRequest);

    UserDTO findUser();

    String deleteUser();

    String updateUser(UUID userId, RegisterUserRequest registerUserRequest);

}
