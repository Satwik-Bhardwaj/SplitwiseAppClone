package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    // create a new expense which is not grouped
    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestBody ExpenseDTO expenseDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.createNonGroupedExpense(userId, expenseDTO));

    }

    // create a new expense which is grouped
    @PostMapping("/create/{groupId}")
    public ResponseEntity<String> createExpense(@PathVariable int groupId, @RequestBody ExpenseDTO expenseDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.createGroupedExpense(userId, groupId, expenseDTO));

    }

    // delete an expense
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable int expenseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        return ResponseEntity.ok(expenseService.deleteExpenseById(expenseId, userId));

    }

    // add payee to the expense
    @PostMapping("/add-payee/{expenseId}")
    public ResponseEntity<String> addPayeeToExpense(@RequestParam int payeeId, @PathVariable int expenseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.addUserToExpense(expenseId, payeeId, userId));

    }

    // remove payee from the expense
    @DeleteMapping("/remove-payee/{expenseId}")
    public ResponseEntity<String> removePayeeFromExpense(@RequestParam int payeeId, @PathVariable int expenseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        return ResponseEntity.ok(expenseService.removeUserFromExpense(expenseId, payeeId, userId));

    }

    // get an expense
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> findExpense(@PathVariable int expenseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.findExpenseById(expenseId, userId));

    }

    // get all expenses within a group
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpense(@PathVariable int groupId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = Integer.parseInt(authentication.getName());

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.findAllExpense(groupId, userId));

    }
}
