package com.satwik.splitwiseclone.persistence.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseListDTO {

    double amount;

    private String description;

    private String expenseCreatedAt;

}
