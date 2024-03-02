package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.configuration.security.LoggedInUser;
import com.satwik.splitwiseclone.persistence.models.Expense;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.ExpenseRepository;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
public class AuthorizationService {

    @Autowired
    LoggedInUser loggedInUser;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    public User getAuthorizedUser() {
        return userRepository.findById(loggedInUser.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User checkAuthorizationOnUser(UUID userId) throws AccessDeniedException {
        User loggedUser = userRepository.findById(loggedInUser.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (loggedUser.getId() != user.getId()) throw  new AccessDeniedException("Access Denied");
        return user;
    }

    public Group checkAuthorizationOnGroup(UUID groupId) throws AccessDeniedException {
        User loggedUser = userRepository.findById(loggedInUser.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found!"));
        if (loggedUser.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");
        return group;
    }

    public Expense checkAuthorizationOnExpense(UUID expenseId) throws AccessDeniedException {
        User loggedUser = userRepository.findById(loggedInUser.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Group not found!"));
        if (loggedUser.getId() != expense.getOwner().getId()) throw new AccessDeniedException("Access Denied");
        return expense;
    }

}
