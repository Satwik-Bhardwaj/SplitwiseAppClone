package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.dto.user.PayerDTO;
import com.satwik.splitwiseclone.persistence.entities.ExpenseShare;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {

    @Query("SELECT NEW com.satwik.splitwiseclone.persistence.dto.user.PayerDTO(u.username, es.sharedAmount) " +
            "FROM ExpenseShare es " +
            "INNER JOIN es.user u " +
            "WHERE es.expense.id = ?1")
    List<PayerDTO> findPayersWithAmountByExpenseId(UUID expenseId);

    @Query(value = "SELECT COUNT(*) FROM ExpenseShare es WHERE es.expense.id = ?1")
    int findCountOfPayer(UUID expenseId);

    @Query("SELECT u.username " +
            "FROM ExpenseShare es " +
            "INNER JOIN es.user u " +
            "WHERE es.expense.id = ?1")
    List<String> findPayersById(UUID expenseId);

    @Query(value = "SELECT es FROM ExpenseShare es WHERE es.expense.id = ?1")
    List<ExpenseShare> findExpenseShareById(UUID expenseId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ExpenseShare es " +
            "WHERE es.expense.id = ?1 AND es.user.id = ?2")
    void deleteByExpenseIdAndUserId(UUID expenseId, UUID payerId);
}
