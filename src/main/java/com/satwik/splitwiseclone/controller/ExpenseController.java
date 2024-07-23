package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    // create a new expense which is not grouped
    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        log.info("Post Endpoint: create expense with request: {}", expenseDTO);
        String response = expenseService.createNonGroupedExpense(expenseDTO);
        log.info("Post Endpoint: create expense with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // create a new expense which is grouped
    @PostMapping("/create/{groupId}")
    public ResponseEntity<String> createExpense(@PathVariable UUID groupId, @RequestBody ExpenseDTO expenseDTO) {
        log.info("Post Endpoint: create grouped expense with request: {} and groupId: {}", expenseDTO, groupId);
        String response = expenseService.createGroupedExpense(groupId, expenseDTO);
        log.info("Post Endpoint: create grouped expense with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // delete an expense
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable UUID expenseId) {
        log.info("Delete Endpoint: delete an expense with id: {}", expenseId);
        String response = expenseService.deleteExpenseById(expenseId);
        log.info("Delete Endpoint: delete an expense with response: {}", response);
        return ResponseEntity.ok(response);
    }

    // add payee to the expense
    @PostMapping("/add-payee/{expenseId}")
    public ResponseEntity<String> addPayeeToExpense(@RequestParam UUID payeeId, @PathVariable UUID expenseId) {
        log.info("Post Endpoint: add payee with payeeId: {}, to an expense with expenseId: {}", payeeId, expenseId);
        String response = expenseService.addUserToExpense(expenseId, payeeId);
        log.info("Post Endpoint: add payee to expense with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // remove payee from the expense
    @DeleteMapping("/remove-payee/{expenseId}")
    public ResponseEntity<String> removePayeeFromExpense(@RequestParam UUID payeeId, @PathVariable UUID expenseId) {
        log.info("Delete Endpoint: remove payee with payeeId: {} from an expense with expenseId: {}", payeeId, expenseId);
        String response = expenseService.removeUserFromExpense(expenseId, payeeId);
        log.info("Delete Endpoint: remove payee from an expense with response: {}", response);
        return ResponseEntity.ok(response);
    }

    // get an expense
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> findExpense(@PathVariable UUID expenseId) {
        log.info("Get Endpoint: find an expense with expenseId: {}", expenseId);
        ExpenseDTO response = expenseService.findExpenseById(expenseId);
        log.info("Get Endpoint: find an expense with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // get all expenses within a group
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpense(@PathVariable UUID groupId) {
        log.info("Get Endpoint: find all expense within a group with groupId: {}", groupId);
        List<ExpenseDTO> response = expenseService.findAllExpense(groupId);
        log.info("Get Endpoint: find all expense within a group with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
