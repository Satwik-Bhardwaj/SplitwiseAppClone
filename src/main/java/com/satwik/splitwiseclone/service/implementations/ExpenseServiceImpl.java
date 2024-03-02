package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.expense.*;
import com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO;
import com.satwik.splitwiseclone.persistence.models.*;
import com.satwik.splitwiseclone.repository.*;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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
    public String createGroupedExpense(UUID groupId, ExpenseDTO expenseDTO) throws Exception {

        User user = authorizationService.getAuthorizedUser();
        Group group = authorizationService.checkAuthorizationOnGroup(groupId);

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
        Group group = groupRepository.findDefaultGroup(user.getId()).orElseThrow(() -> new RuntimeException("Group not found"));

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
    public String deleteExpenseById(UUID expenseId) throws AccessDeniedException {

        authorizationService.checkAuthorizationOnExpense(expenseId);
        expenseRepository.deleteById(expenseId);

        return "Expense is deleted successfully!";

    }

    @Override
    @Transactional
    public String addUserToExpense(UUID expenseId, UUID payeeId) throws AccessDeniedException {

        Expense expense = authorizationService.checkAuthorizationOnExpense(expenseId);
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new RuntimeException("Payee not found"));

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
    public String removeUserFromExpense(UUID expenseId, UUID payeeId) throws AccessDeniedException {

        authorizationService.checkAuthorizationOnExpense(expenseId);

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
        User payee = userRepository.findById(payeeId).orElseThrow(() -> new RuntimeException("Payee not found"));

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
    public ExpenseDTO findExpenseById(UUID expenseId) throws AccessDeniedException {

        authorizationService.checkAuthorizationOnExpense(expenseId);

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));

        ExpenseDTO expenseDTO = new ExpenseDTO();
        List<PayeeDTO> payeeDTOS = expenseShareRepository.findPayeesWithAmountByExpenseId(expense.getId());

        expenseDTO.setPayees(payeeDTOS);
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setPayerName(expense.getOwner().getUsername());

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> findAllExpense(UUID groupId) throws AccessDeniedException {

        authorizationService.checkAuthorizationOnGroup(groupId);
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

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
