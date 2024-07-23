package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    String createNonGroupedExpense(ExpenseDTO expenseDTO);

    String createGroupedExpense(UUID groupId, ExpenseDTO expenseDTO);

    String deleteExpenseById(UUID expenseId);

    String addUserToExpense(UUID expenseId, UUID payeeId);

    String removeUserFromExpense(UUID expenseId, UUID payeeId);

    ExpenseDTO findExpenseById(UUID expenseId);

    List<ExpenseDTO> findAllExpense(UUID groupId);

}
