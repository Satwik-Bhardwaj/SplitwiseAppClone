package com.satwik.splitwiseclone.persistence.dto.user;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayeeDTO {

    String username;

    double amount;

}
