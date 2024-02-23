package com.satwik.splitwiseclone.persistence.dto.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseListDTO {

    @NotNull
    double amount;

    private String description;

    @NotNull
    @NotBlank
    private String expenseCreatedAt;

}
