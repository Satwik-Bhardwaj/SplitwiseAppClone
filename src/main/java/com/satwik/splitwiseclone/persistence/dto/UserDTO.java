package com.satwik.splitwiseclone.persistence.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    String username;

    String email;

    PhoneDTO phone;

    String accountCreatedAt;

}
