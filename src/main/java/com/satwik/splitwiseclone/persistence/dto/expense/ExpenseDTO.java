package com.satwik.splitwiseclone.persistence.dto.expense;

import com.satwik.splitwiseclone.persistence.dto.user.PayerDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

    private String payerName;

    @NotNull
    private double amount;

    @NotNull
    private String description;

    private String date;

    private List<PayerDTO> payers;

}
