package com.satwik.splitwiseclone.persistence.dto;

import com.satwik.splitwiseclone.persistence.models.ExpenseShare;
import com.satwik.splitwiseclone.persistence.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

    private int groupId;

    private double amount;

    private String description;

    private String date;

    private List<ExpenseShare> expenseShareList;

}
