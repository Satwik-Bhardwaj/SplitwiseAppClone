package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseDTO;
import com.satwik.splitwiseclone.service.interfaces.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    // create a new expense which is not grouped
    // TODO : user id might not required after implementing spring security
    @PostMapping("/create")
    public ResponseEntity<String> createExpense(@RequestParam int userId, @RequestBody ExpenseDTO expenseDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.createNonGroupedExpense(userId, expenseDTO));

    }

    // create a new expense which is grouped
    @PostMapping("/create/{groupId}")
    public ResponseEntity<String> createExpense(@RequestParam int userId, @PathVariable int groupId, @RequestBody ExpenseDTO expenseDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.createGroupedExpense(userId, groupId, expenseDTO));

    }

    // delete an expense
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable int expenseId) {

        return ResponseEntity.ok(expenseService.deleteExpenseById(expenseId));

    }

    // add payee to the expense
    @PostMapping("/add-payee/{expenseId}")
    public ResponseEntity<String> addPayeeToExpense(@RequestParam int payeeId, @PathVariable int expenseId) {

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.addUserToExpense(expenseId, payeeId));

    }

    // remove payee from the expense
    @DeleteMapping("/remove-payee/{expenseId}")
    public ResponseEntity<String> removePayeeFromExpense(@RequestParam int payeeId, @PathVariable int expenseId) {

        return ResponseEntity.ok(expenseService.removeUserFromExpense(expenseId, payeeId));

    }

    // get an expense
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> findExpense(@PathVariable int expenseId) {

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.findExpenseById(expenseId));

    }

    // get all expenses within a group
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseDTO>> findAllExpense(@PathVariable int groupId) {

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.findAllExpense(groupId));

    }


}
