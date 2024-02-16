package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "expense_share")
public class ExpenseShare {

    @Id
    @Column(name = "sharing_expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "shared_amount")
    private double sharedAmount;

}
