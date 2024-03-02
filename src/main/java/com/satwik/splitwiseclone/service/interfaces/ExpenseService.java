package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    String createNonGroupedExpense(ExpenseDTO expenseDTO) throws Exception;

    String createGroupedExpense(UUID groupId, ExpenseDTO expenseDTO) throws Exception;

    String deleteExpenseById(UUID expenseId) throws AccessDeniedException;

    String addUserToExpense(UUID expenseId, UUID payeeId) throws AccessDeniedException;

    String removeUserFromExpense(UUID expenseId, UUID payeeId) throws AccessDeniedException;

    ExpenseDTO findExpenseById(UUID expenseId) throws AccessDeniedException;

    List<ExpenseDTO> findAllExpense(UUID groupId) throws AccessDeniedException;

}
