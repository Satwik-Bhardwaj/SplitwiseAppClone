package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.PayeeDTO;
import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;
import com.satwik.splitwiseclone.persistence.models.Expense;
import com.satwik.splitwiseclone.persistence.models.ExpenseShare;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.ExpenseRepository;
import com.satwik.splitwiseclone.repository.ExpenseShareRepository;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // fetch the user
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) return "User not present.";
        User fetchedUser = user.get();

        // fetch the default group
        Optional<Group> group = groupRepository.findDefaultGroup(userId);

        if (!group.isPresent()) return "Default group not found.";

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
        if(!user.isPresent()) return "User not present.";
        User fetchedUser = user.get();

        Optional<Group> group = groupRepository.findById(groupId);

        // TODO : create group~ exception
        if(!group.isPresent()) return "Group not found";

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setDate(expenseDTO.getDate());
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

        // get the expense
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        if (!expense.isPresent()) return "Expense doesn't exist.";
        Expense fetchedExpense = expense.get();

        // get the user
        Optional<User> user = userRepository.findById(payeeId);
        if (!user.isPresent()) return "Payee doesn't exist.";
        User fetchedUser = user.get();

        // assign user to expense
        ExpenseShare expenseShare = new ExpenseShare();
        expenseShare.setExpense(fetchedExpense);
        expenseShare.setUser(fetchedUser);
//        expenseShare.setSharedAmount(fetchedExpense.getAmount() / (getTotalNoPayee() + 1));
        // TODO : update every payee's amount
        // saving the user
        expenseShareRepository.save(expenseShare);

        return "Payee is added to the expense.";
    }

    @Override
    public String removeUserFromExpense(int expenseId, int payeeId) {
        return null;
    }

//    @Override
//    public ExpenseDTO findExpenseById(int expenseId) {
//        return null;
//    }

    @Override
    public ExpenseDTO findExpenseById(int expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        if(!expense.isPresent()) return null;

        Expense fetchedExpense = expense.get();
        ExpenseDTO expenseDTO = new ExpenseDTO();
        List<PayeeDTO> payeeDTOS = expenseShareRepository.findPayeesWithGradesByExpenseId(expenseId);

        expenseDTO.setPayees(payeeDTOS);
        expenseDTO.setDate(fetchedExpense.getDate());
        expenseDTO.setDescription(fetchedExpense.getDescription());
        expenseDTO.setAmount(fetchedExpense.getAmount());
        expenseDTO.setPayerName(fetchedExpense.getUser().getUsername());

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> findAllExpense(int userId) {
        return null;
    }

}
