package com.satwik.splitwiseclone.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

    String username;

    String email;

    PhoneDTO phoneDTO;

    String password;

}
