package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;

import java.util.List;

public interface ExpenseService {
    String createNonGroupedExpense(int userId, ExpenseDTO expenseDTO);

    String createGroupedExpense(int userId, int groupId, ExpenseDTO expenseDTO);

    String deleteExpenseById(int expenseId, int userId);

    String addUserToExpense(int expenseId, int payeeId, int userId);

    String removeUserFromExpense(int expenseId, int payeeId, int userId);

    ExpenseDTO findExpenseById(int expenseId, int userId);

    List<ExpenseDTO> findAllExpense(int groupId, int userId);

}
