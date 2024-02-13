package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
}
