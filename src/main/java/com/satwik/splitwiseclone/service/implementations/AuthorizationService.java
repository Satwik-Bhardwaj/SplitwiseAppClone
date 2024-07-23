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


    public User getAuthorizedUser() {
        return userRepository.findByEmail(loggedInUser.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
