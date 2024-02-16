package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.dto.PayeeDTO;
import com.satwik.splitwiseclone.persistence.models.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, Integer> {

    @Query(value = "SELECT p.username, es.amount FROM user p JOIN expense_share es ON p.id = es.user.user_id WHERE es.expense.expense_id = :expenseId", nativeQuery = true)
    List<PayeeDTO> findPayeesWithGradesByExpenseId(@Param("expenseId") int expenseId);

}
