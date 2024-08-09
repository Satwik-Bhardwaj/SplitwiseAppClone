package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.exception.BadRequestException;
import com.satwik.splitwiseclone.exception.DataNotFoundException;
import com.satwik.splitwiseclone.persistence.dto.expense.*;
import com.satwik.splitwiseclone.persistence.dto.user.PayerDTO;
import com.satwik.splitwiseclone.persistence.entities.*;
import com.satwik.splitwiseclone.repository.*;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseShareRepository expenseShareRepository;


    @Override
    @Transactional
    public String createGroupedExpense(UUID groupId, ExpenseDTO expenseDTO) {

        User user = authorizationService.getAuthorizedUser();
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new DataNotFoundException("Group not found"));
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
    public String createNonGroupedExpense(ExpenseDTO expenseDTO) {

        User user = authorizationService.getAuthorizedUser();
        Group group = groupRepository.findDefaultGroup(user.getId()).orElseThrow(() -> new DataNotFoundException("Group not found"));

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setGroup(group);
        expense.setOwner(user);
        expense = expenseRepository.save(expense);

        // TODO: add owner itself for
        return expense.getId() + " - Expense successfully created in the default group!";
    }

    @Override
    @Transactional
    public String deleteExpenseById(UUID expenseId) {
        expenseRepository.deleteById(expenseId);
        return "Expense is deleted successfully!";
    }

    @Override
    @Transactional
    public String addUserToExpense(UUID expenseId, UUID payerId) {

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new RuntimeException("Payee not found"));
        ExpenseShare expenseShare = new ExpenseShare();
        expenseShare.setExpense(expense);
        expenseShare.setUser(payer);
        double sharedAmount = expense.getAmount() /
                (expenseShareRepository.findCountOfPayer(expenseId) + 1);
        expenseShare.setSharedAmount(sharedAmount);
        List<ExpenseShare> shareList = expenseShareRepository.findExpenseShareById(expenseId);
        for (ExpenseShare share : shareList) {
            share.setSharedAmount(sharedAmount);
            expenseShareRepository.save(share);
        }
        expenseShareRepository.save(expenseShare);
        return "Success! Payer is added to the expense.";
    }

    @Override
    @Transactional
    public String removeUserFromExpense(UUID expenseId, UUID payerId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new DataNotFoundException("Expense not found"));
        User payer = userRepository.findById(payerId).orElseThrow(() -> new DataNotFoundException("Payer not found"));
        expenseShareRepository.deleteByExpenseIdAndUserId(expense.getId(), payer.getId());
        double sharedAmount = expense.getAmount() / (expenseShareRepository.findCountOfPayer(expense.getId()));
        List<ExpenseShare> shareList = expenseShareRepository.findExpenseShareById(expense.getId());
        for (ExpenseShare share : shareList) {
            share.setSharedAmount(sharedAmount);
            expenseShareRepository.save(share);
        }
        return "Success! Payer is removed from the expense";
    }

    @Override
    public ExpenseDTO findExpenseById(UUID expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new DataNotFoundException("Expense not found"));
        ExpenseDTO expenseDTO = new ExpenseDTO();
        List<PayerDTO> payerDTOS = expenseShareRepository.findPayersWithAmountByExpenseId(expense.getId());
        expenseDTO.setPayers(payerDTOS);
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setPayerName(expense.getOwner().getUsername());
        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> findAllExpense(UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new DataNotFoundException("Group not found"));
        List<Expense> expenses = expenseRepository.findByGroupId(group.getId());
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        for (Expense expense : expenses) {
            ExpenseDTO expenseDTO = new ExpenseDTO();
            List<PayerDTO> payerDTOS = expenseShareRepository.findPayersWithAmountByExpenseId(expense.getId());
            expenseDTO.setPayers(payerDTOS);
            expenseDTO.setDescription(expense.getDescription());
            expenseDTO.setAmount(expense.getAmount());
            expenseDTO.setPayerName(expense.getOwner().getUsername());
            expenseDTOS.add(expenseDTO);
        }
        return expenseDTOS;
    }
}