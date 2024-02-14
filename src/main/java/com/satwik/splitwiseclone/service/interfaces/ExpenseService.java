package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.ExpenseDTO;
import com.satwik.splitwiseclone.persistence.models.Expense;

import java.util.List;

public interface ExpenseService {
    String createNonGroupedExpense(int userId, ExpenseDTO expenseDTO);

    String createGroupedExpense(int userId, int groupId, ExpenseDTO expenseDTO);

    String deleteExpenseById(int expenseId);

    String addUserToExpense(int expenseId, int payeeId);

    String removeUserFromExpense(int expenseId, int payeeId);

    ExpenseDTO findExpenseById(int expenseId);

    List<ExpenseDTO> findAllExpense(int groupId);

//    // create new expense in a Group
//    String createExpenseInGroup(ExpenseDTO expenseDTO, int groupId, int userId);
//
//    // create new expense not in a group
//    String createExpense(ExpenseDTO expenseDTO, int userId);
//
//    // delete expense
//    String deleteExpenseById(int expenseId);
//
//    // find all expense
//    List<ExpenseDTO> findAllExpense(int userId);
//
//    // find new expense
//    ExpenseDTO findExpenseById(int expenseId, int userId);
//
//
//    // delete an expense
//    String deleteExpenseById(int expenseId, int userId);
//
//    // add user to expense
//    String addUserToExpense(int expenseId, int userId, int payerId);

}
