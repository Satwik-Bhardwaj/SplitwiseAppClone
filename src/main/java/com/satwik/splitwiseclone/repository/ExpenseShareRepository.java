package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO;
import com.satwik.splitwiseclone.persistence.models.ExpenseShare;
import com.satwik.splitwiseclone.persistence.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, Integer> {

//    @Query(value = "SELECT user.username AS username, expense_share.shared_amount AS amount FROM expense_share INNER JOIN user ON expense_share.user_id = user.user_id WHERE expense_share.expense_id = ?1", nativeQuery = true)
//    List<PayeeDTO> findPayeesWithAmountByExpenseId(int expenseId);

    @Query("SELECT NEW com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO(u.username, es.sharedAmount) " +
            "FROM ExpenseShare es " +
            "INNER JOIN es.user u " +
            "WHERE es.expense.id = ?1")
    List<PayeeDTO> findPayeesWithAmountByExpenseId(int expenseId);

    @Query(value = "SELECT COUNT(*) FROM ExpenseShare es WHERE es.expense.id = ?1")
    int findCountOfPayee(int expenseId);

    @Query(value = "SELECT es FROM ExpenseShare es WHERE es.expense.id = ?1")
    List<ExpenseShare> findExpenseShareById(int expenseId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ExpenseShare es " +
            "WHERE es.expense.id = ?1 AND es.user.id = ?2")
    void deleteByExpenseIdAndUserId(int expenseId, int payeeId);
}
