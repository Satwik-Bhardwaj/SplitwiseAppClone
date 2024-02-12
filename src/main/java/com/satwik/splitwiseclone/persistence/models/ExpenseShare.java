package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Expense_Share")
public class ExpenseShare {

    @Id
    @Column(name = "sharing_expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;



}
