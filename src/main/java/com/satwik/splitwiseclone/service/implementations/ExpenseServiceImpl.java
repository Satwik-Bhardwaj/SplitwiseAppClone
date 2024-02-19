package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.expense.*;
import com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO;
import com.satwik.splitwiseclone.persistence.models.*;
import com.satwik.splitwiseclone.repository.*;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseShareRepository expenseShareRepository;

    @Override
    @Transactional
    public String createNonGroupedExpense(int userId, ExpenseDTO expenseDTO) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findDefaultGroup(userId).orElseThrow(() -> new RuntimeException("Group not found"));

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        expense.setGroup(group);
        expense.setUser(user);
        expenseRepository.save(expense);

        return "Expense successfully created in the default group!";
    }

    @Override
    @Transactional
    public String createGroupedExpense(int userId, int groupId, ExpenseDTO expenseDTO) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not fount"));

        if(user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        expense.setGroup(group);
        expense.setUser(user);
        expenseRepository.save(expense);

        return "Expense successfully created!";
    }

    @Override
    @Transactional
    public String deleteExpenseById(int expenseId, int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));

        if(user == null || user.getId() != expense.getUser().getId()) throw new AccessDeniedException("Access Denied");

        expenseRepository.deleteById(expenseId);

        return "Expense is deleted successfully!";

    }

    @Override
    @Transactional
    public String addUserToExpense(int expenseId, int payeeId, int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new RuntimeException("Payee not found"));

        if(user == null || user.getId() != expense.getUser().getId()) throw new AccessDeniedException("Access Denied");

        ExpenseShare expenseShare = new ExpenseShare();
        expenseShare.setExpense(expense);
        expenseShare.setUser(payee);

        double sharedAmount = expense.getAmount() /
                (expenseShareRepository.findCountOfPayee(expenseId) + 1);

        expenseShare.setSharedAmount(sharedAmount);

        List<ExpenseShare> shareList = expenseShareRepository.findExpenseShareById(expenseId);

        for (ExpenseShare share : shareList) {
            share.setSharedAmount(sharedAmount);
            expenseShareRepository.save(share);
        }

        expenseShareRepository.save(expenseShare);

        return "Success! Payee is added to the expense.";
    }

    @Override
    @Transactional
    public String removeUserFromExpense(int expenseId, int payeeId, int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new RuntimeException("Payee not found"));

        if(user == null || user.getId() != expense.getUser().getId()) throw new AccessDeniedException("Access Denied");

        expenseShareRepository.deleteByExpenseIdAndUserId(expense.getId(), payee.getId());

        double sharedAmount = expense.getAmount() / (expenseShareRepository.findCountOfPayee(expense.getId()));
        List<ExpenseShare> shareList = expenseShareRepository.findExpenseShareById(expense.getId());

        for (ExpenseShare share : shareList) {
            share.setSharedAmount(sharedAmount);
            expenseShareRepository.save(share);
        }

        return "Success! Payee is removed from the expense";
    }

    @Override
    public ExpenseDTO findExpenseById(int expenseId, int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));

        if(user == null || user.getId() != expense.getUser().getId()) throw new AccessDeniedException("Access Denied");

        ExpenseDTO expenseDTO = new ExpenseDTO();
        List<PayeeDTO> payeeDTOS = expenseShareRepository.findPayeesWithAmountByExpenseId(expense.getId());

        expenseDTO.setPayees(payeeDTOS);
        expenseDTO.setDate(expense.getDate());
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setPayerName(expense.getUser().getUsername());

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> findAllExpense(int groupId, int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        if(user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");

        List<Expense> expenses = expenseRepository.findByGroupId(group.getId());
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();

        for (Expense expense : expenses) {

            ExpenseDTO expenseDTO = new ExpenseDTO();
            List<PayeeDTO> payeeDTOS = expenseShareRepository.findPayeesWithAmountByExpenseId(expense.getId());

            expenseDTO.setPayees(payeeDTOS);
            expenseDTO.setDate(expense.getDate());
            expenseDTO.setDescription(expense.getDescription());
            expenseDTO.setAmount(expense.getAmount());
            expenseDTO.setPayerName(expense.getUser().getUsername());
            expenseDTO.setPayees(payeeDTOS);
            expenseDTOS.add(expenseDTO);
        }

        return expenseDTOS;
    }
}
