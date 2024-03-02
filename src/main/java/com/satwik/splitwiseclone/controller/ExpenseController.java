package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    // create a new expense which is not grouped
    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDTO expenseDTO) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(expenseService.createNonGroupedExpense(expenseDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // create a new expense which is grouped
    @PostMapping("/create/{groupId}")
    public ResponseEntity<String> createExpense(@PathVariable UUID groupId, @RequestBody ExpenseDTO expenseDTO) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(expenseService.createGroupedExpense(groupId, expenseDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // delete an expense
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable UUID expenseId) {

        try {
            return ResponseEntity.ok(expenseService.deleteExpenseById(expenseId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

    }

    // add payee to the expense
    @PostMapping("/add-payee/{expenseId}")
    public ResponseEntity<String> addPayeeToExpense(@RequestParam UUID payeeId, @PathVariable UUID expenseId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(expenseService.addUserToExpense(expenseId, payeeId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

    }

    // remove payee from the expense
    @DeleteMapping("/remove-payee/{expenseId}")
    public ResponseEntity<String> removePayeeFromExpense(@RequestParam UUID payeeId, @PathVariable UUID expenseId) {

        try {
            return ResponseEntity.ok(expenseService.removeUserFromExpense(expenseId, payeeId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

    }

    // get an expense
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> findExpense(@PathVariable UUID expenseId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(expenseService.findExpenseById(expenseId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

    }

    // get all expenses within a group
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpense(@PathVariable UUID groupId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(expenseService.findAllExpense(groupId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}
