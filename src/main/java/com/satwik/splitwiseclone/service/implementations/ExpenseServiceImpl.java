package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.expense.*;
import com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO;
import com.satwik.splitwiseclone.persistence.models.*;
import com.satwik.splitwiseclone.repository.*;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

        Optional<User> user = userRepository.findById(userId);
        Optional<Group> group = groupRepository.findDefaultGroup(userId);

        if(!user.isPresent()) return "User not present.";
        if (!group.isPresent()) return "Default group not found.";

        User fetchedUser = user.get();

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setDate(expenseDTO.getDate());
        expense.setGroup(group.get());
        expense.setUser(user.get());
        expenseRepository.save(expense);

        return "Expense successfully created in default group!";
    }

    @Override
    @Transactional
    public String createGroupedExpense(int userId, int groupId, ExpenseDTO expenseDTO) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Group> group = groupRepository.findById(groupId);

        if(!user.isPresent()) return "User not present.";
        // TODO : create group~ exception
        if(!group.isPresent()) return "Group not found";

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        expense.setGroup(group.get());
        expense.setUser(user.get());
        expenseRepository.save(expense);

        return "Expense successfully created!";
    }

    @Override
    @Transactional
    public String deleteExpenseById(int expenseId) {

        expenseRepository.deleteById(expenseId);
        return "Expense deleted successfully!";

    }

    @Override
    @Transactional
    public String addUserToExpense(int expenseId, int payeeId) {

        Optional<Expense> expense = expenseRepository.findById(expenseId);
        Optional<User> user = userRepository.findById(payeeId);

        if (!expense.isPresent()) return "Expense doesn't exist.";
        if (!user.isPresent()) return "Payee doesn't exist.";

        Expense fetchedExpense = expense.get();
        User fetchedUser = user.get();

        ExpenseShare expenseShare = new ExpenseShare();
        expenseShare.setExpense(fetchedExpense);
        expenseShare.setUser(fetchedUser);

        double sharedAmount = fetchedExpense.getAmount() /
                (expenseShareRepository.findCountOfPayee(expenseId) + 1);

        expenseShare.setSharedAmount(sharedAmount);

        List<ExpenseShare> shareList = expenseShareRepository.findExpenseShareById(expenseId);

        for (ExpenseShare share : shareList) {

            share.setSharedAmount(sharedAmount);
            expenseShareRepository.save(share);

        }

        expenseShareRepository.save(expenseShare);

        return "Payee is added to the expense.";
    }

    @Override
    @Transactional
    public String removeUserFromExpense(int expenseId, int payeeId) {

        expenseShareRepository.deleteByExpenseIdAndUserId(expenseId, payeeId);

        Optional<Expense> expense = expenseRepository.findById(expenseId);
        if (!expense.isPresent()) return "Expense doesn't exist.";
        Expense fetchedExpense = expense.get();

        double sharedAmount = fetchedExpense.getAmount() / (expenseShareRepository.findCountOfPayee(expenseId));

        List<ExpenseShare> shareList = expenseShareRepository.findExpenseShareById(expenseId);

        for (ExpenseShare share : shareList) {
            share.setSharedAmount(sharedAmount);
            expenseShareRepository.save(share);
        }

        return "Payee are successfully remove form the expense";
    }

    @Override
    public ExpenseDTO findExpenseById(int expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        if(!expense.isPresent()) return null;

        Expense fetchedExpense = expense.get();
        ExpenseDTO expenseDTO = new ExpenseDTO();
        List<PayeeDTO> payeeDTOS = expenseShareRepository.findPayeesWithAmountByExpenseId(expenseId);

        expenseDTO.setPayees(payeeDTOS);
        expenseDTO.setDate(fetchedExpense.getDate());
        expenseDTO.setDescription(fetchedExpense.getDescription());
        expenseDTO.setAmount(fetchedExpense.getAmount());
        expenseDTO.setPayerName(fetchedExpense.getUser().getUsername());

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> findAllExpense(int groupId) {

        List<Expense> expenses = expenseRepository.findByGroupId(groupId);
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
