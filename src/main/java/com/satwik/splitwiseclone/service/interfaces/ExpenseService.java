package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    String createNonGroupedExpense(UUID userId, ExpenseDTO expenseDTO);

    String createGroupedExpense(UUID userId, UUID groupId, ExpenseDTO expenseDTO);

    String deleteExpenseById(UUID expenseId, UUID userId);

    String addUserToExpense(UUID expenseId, UUID payeeId, UUID userId);

    String removeUserFromExpense(UUID expenseId, UUID payeeId, UUID userId);

    ExpenseDTO findExpenseById(UUID expenseId, UUID userId);

    List<ExpenseDTO> findAllExpense(UUID groupId, UUID userId);

}
