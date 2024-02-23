package com.satwik.splitwiseclone.persistence.dto.expense;

import com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

    @NotNull
    @NotBlank
    private String payerName;

    @NotNull
    private double amount;

    private String description;

    @NotNull
    @NotBlank
    private String date;

    private List<PayeeDTO> payees;

}
