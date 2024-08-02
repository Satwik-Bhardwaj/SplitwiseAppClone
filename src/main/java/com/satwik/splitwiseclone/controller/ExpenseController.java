package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    /**
     * Creates a new expense which is not grouped.
     *
     * This endpoint processes the request to create a non-grouped expense. It logs the
     * incoming request and the resulting response.
     *
     * @param expenseDTO the request body containing the expense details.
     * @return a ResponseEntity containing a string response message indicating the
     *         result of the expense creation process.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        log.info("Post Endpoint: create expense with request: {}", expenseDTO);
        String response = expenseService.createNonGroupedExpense(expenseDTO);
        log.info("Post Endpoint: create expense with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Creates a new expense which is grouped.
     *
     * This endpoint processes the request to create a grouped expense. It logs the
     * incoming request and the resulting response.
     *
     * @param groupId the UUID of the group to which the expense belongs.
     * @param expenseDTO the request body containing the expense details.
     * @return a ResponseEntity containing a string response message indicating the
     *         result of the grouped expense creation process.
     */
    @PostMapping("/create/{groupId}")
    public ResponseEntity<String> createExpense(@PathVariable UUID groupId, @RequestBody ExpenseDTO expenseDTO) {
        log.info("Post Endpoint: create grouped expense with request: {} and groupId: {}", expenseDTO, groupId);
        String response = expenseService.createGroupedExpense(groupId, expenseDTO);
        log.info("Post Endpoint: create grouped expense with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Deletes an expense by its ID.
     *
     * This endpoint processes the request to delete an expense identified by the given ID.
     * It logs the incoming request and the resulting response.
     *
     * @param expenseId the UUID of the expense to be deleted.
     * @return a ResponseEntity containing a string response message indicating the
     *         result of the expense deletion process.
     */
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable UUID expenseId) {
        log.info("Delete Endpoint: delete an expense with id: {}", expenseId);
        String response = expenseService.deleteExpenseById(expenseId);
        log.info("Delete Endpoint: delete an expense with response: {}", response);
        return ResponseEntity.ok(response);
    }

    // add payer to the expense
    @PostMapping("/add-payer/{expenseId}")
    public ResponseEntity<String> addPayerToExpense(@RequestParam UUID payerId, @PathVariable UUID expenseId) {
        log.info("Post Endpoint: add payer with payerId: {}, to an expense with expenseId: {}", payerId, expenseId);
        String response = expenseService.addUserToExpense(expenseId, payerId);
        log.info("Post Endpoint: add payer to expense with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // remove payer from the expense
    @DeleteMapping("/remove-payer/{expenseId}")
    public ResponseEntity<String> removePayerFromExpense(@RequestParam UUID payerId, @PathVariable UUID expenseId) {
        log.info("Delete Endpoint: remove payer with payerId: {} from an expense with expenseId: {}", payerId, expenseId);
        String response = expenseService.removeUserFromExpense(expenseId, payerId);
        log.info("Delete Endpoint: remove payer from an expense with response: {}", response);
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
