package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Expense_Share")
public class ExpenseShare {

    @EmbeddedId
    private SharingExpenseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("expenseId")
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @Column(name = "shared_amount")
    private double sharedAmount;

}
