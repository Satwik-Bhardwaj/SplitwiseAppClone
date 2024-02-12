package com.satwik.splitwiseclone.persistence.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SharingExpenseId implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "expense_id")
    private int expenseId;

}
