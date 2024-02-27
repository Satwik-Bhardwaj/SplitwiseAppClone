package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.configuration.security.LoggedInUser;
import com.satwik.splitwiseclone.persistence.dto.expense.*;
import com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO;
import com.satwik.splitwiseclone.persistence.models.*;
import com.satwik.splitwiseclone.repository.*;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private LoggedInUser loggedInUser;

    @Override
    @Transactional
    public String createGroupedExpense(UUID userId, UUID groupId, ExpenseDTO expenseDTO) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not fount"));

        if(user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");
        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setGroup(group);
        expense.setOwner(user);
        expenseRepository.save(expense);

        return "Expense successfully created!";
    }

    @Override
    @Transactional
    public String createNonGroupedExpense(UUID userId, ExpenseDTO expenseDTO) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findDefaultGroup(userId).orElseThrow(() -> new RuntimeException("Group not found"));

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());expense.setGroup(group);
        expense.setOwner(user);
        expense = expenseRepository.save(expense);

        // TODO: add owner itself for
        return expense.getId() + " - Expense successfully created in the default group!";
    }

    @Override
    @Transactional
    public String deleteExpenseById(UUID expenseId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));

        if(user == null || user.getId() != expense.getOwner().getId()) throw new AccessDeniedException("Access Denied");

        expenseRepository.deleteById(expenseId);

        return "Expense is deleted successfully!";

    }

    @Override
    @Transactional
    public String addUserToExpense(UUID expenseId, UUID payeeId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new RuntimeException("Payee not found"));

        if(user == null || user.getId() != expense.getOwner().getId()) throw new AccessDeniedException("Access Denied");

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
    public String removeUserFromExpense(UUID expenseId, UUID payeeId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new RuntimeException("Payee not found"));

        if(user == null || user.getId() != expense.getOwner().getId()) throw new AccessDeniedException("Access Denied");

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
    public ExpenseDTO findExpenseById(UUID expenseId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));

        if(user == null || user.getId() != expense.getOwner().getId()) throw new AccessDeniedException("Access Denied");

        ExpenseDTO expenseDTO = new ExpenseDTO();
        List<PayeeDTO> payeeDTOS = expenseShareRepository.findPayeesWithAmountByExpenseId(expense.getId());

        expenseDTO.setPayees(payeeDTOS);
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setPayerName(expense.getOwner().getUsername());

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> findAllExpense(UUID groupId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        if(user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");

        List<Expense> expenses = expenseRepository.findByGroupId(group.getId());
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();

        for (Expense expense : expenses) {

            ExpenseDTO expenseDTO = new ExpenseDTO();
            List<PayeeDTO> payeeDTOS = expenseShareRepository.findPayeesWithAmountByExpenseId(expense.getId());

            expenseDTO.setPayees(payeeDTOS);
            expenseDTO.setDescription(expense.getDescription());
            expenseDTO.setAmount(expense.getAmount());
            expenseDTO.setPayerName(expense.getOwner().getUsername());
            expenseDTO.setPayees(payeeDTOS);
            expenseDTOS.add(expenseDTO);
        }

        return expenseDTOS;
    }
}
