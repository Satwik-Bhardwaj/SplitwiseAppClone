package com.satwik.splitwiseclone.persistence.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempReport {

    UUID expenseId;

    String groupName;

    String expenseName;

    String expenseOwner;

    double totalExpenseAmount;

}
