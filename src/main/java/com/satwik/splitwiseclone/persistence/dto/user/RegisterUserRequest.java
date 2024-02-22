package com.satwik.splitwiseclone.persistence.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

    @NonNull
    String username;

    String email;

    PhoneDTO phone;

    String password;

}
