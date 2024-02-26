package com.satwik.splitwiseclone.persistence.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    @NotNull
    String groupName;

    @NotNull
    String expenseName;

    @NotNull
    @NotBlank
    String expenseOwner;

    @NotNull
    List<String> expenseContributors;

    @NotNull
    double totalExpenseAmount;

}
