package com.satwik.splitwiseclone.persistence.dto.user;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    String username;

    @NotNull
    String email;

    @NotNull
    PhoneDTO phone;

}
