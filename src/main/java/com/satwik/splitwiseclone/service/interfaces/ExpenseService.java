package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;

import java.util.List;

public interface ExpenseService {
    String createNonGroupedExpense(int userId, ExpenseDTO expenseDTO);

    String createGroupedExpense(int userId, int groupId, ExpenseDTO expenseDTO);

    String deleteExpenseById(int expenseId);

    String addUserToExpense(int expenseId, int payeeId);

    String removeUserFromExpense(int expenseId, int payeeId);

    ExpenseDTO findExpenseById(int expenseId);

    List<ExpenseDTO> findAllExpense(int groupId);

}
