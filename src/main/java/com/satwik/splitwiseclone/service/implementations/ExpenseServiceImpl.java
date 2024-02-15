package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;
import com.satwik.splitwiseclone.repository.ExpenseRepository;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    GroupRepository groupRepository;

//    @Override
//    public String createExpenseInGroup(ExpenseDTO expenseDTO, int groupId, int userId) {
//
//        // fetch the group first
//        Optional<Group> group = groupRepository.findById(groupId);
//
//        // TODO : create group~ exception
//        if(!group.isPresent()) return "Group not found";
//
//        Expense expense = new Expense();
//        expense.setAmount(expenseDTO.getAmount());
//        expense.setDescription(expenseDTO.getDescription());
//        expense.setDate(expenseDTO.getDate());
//        expense.setGroup(group.get());
//
//        return "Expense successfully created!";
//    }
//
//    @Override
//    public String createExpense(ExpenseDTO expenseDTO, int userId) {
//
//        // fetch the non group first
//        Optional<Group> group = groupRepository.findById(0);
//
//        Expense expense = new Expense();
//        expense.setAmount(expenseDTO.getAmount());
//        expense.setDescription(expenseDTO.getDescription());
//        expense.setDate(expenseDTO.getDate());
//        expense.setGroup(group.get());
//
//        return "Expense successfully created!";
//
//    }

    @Override
    public String createNonGroupedExpense(int userId, ExpenseDTO expenseDTO) {
        return null;
    }

    @Override
    public String createGroupedExpense(int userId, int groupId, ExpenseDTO expenseDTO) {
        return null;
    }

    @Override
    public String deleteExpenseById(int expenseId) {
        return null;
    }

    @Override
    public String addUserToExpense(int expenseId, int payeeId) {
        return null;
    }

    @Override
    public String removeUserFromExpense(int expenseId, int payeeId) {
        return null;
    }

    @Override
    public ExpenseDTO findExpenseById(int expenseId) {
        return null;
    }

    @Override
    public List<ExpenseDTO> findAllExpense(int userId) {
        return null;
    }

//    @Override
//    public ExpenseDTO findExpenseById(int expenseId, int userId) {
//        return null;
//    }
//
//    @Override
//    public String deleteExpenseById(int expenseId, int userId) {
//        return null;
//    }
//
//    @Override
//    public String addUserToExpense(int expenseId, int userId, int payerId) {
//        return null;
//    }
}
