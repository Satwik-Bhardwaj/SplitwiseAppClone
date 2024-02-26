package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;
import com.satwik.splitwiseclone.persistence.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findByUserId (UUID userId);

    @Query(value = "SELECT * FROM group_table g WHERE g.default_group = TRUE AND g.user_id = ?1", nativeQuery = true)
    Optional<Group> findDefaultGroup(UUID userId);



//    @Query("SELECT NEW com.satwik.splitwiseclone.persistence.dto.report.ReportDTO(u.username, es.sharedAmount) " +
//            "FROM ExpenseShare es " +
//            "INNER JOIN es.user u " +
//            "WHERE es.expense.id = ?1")

    @Query("SELECT NEW com.satwik.splitwiseclone.persistence.dto.report.ReportDTO(g.groupName," +
            "e.description AS expenseName, " +
            "u.username AS expenseOwner, " +
            "us.username AS expenseContributor, " +
            "SUM(es.sharedAmount) AS totalAmount " +
            "FROM Group g " +
            "JOIN g.expenseList e " +
            "JOIN e.owner u " +
            "JOIN e.expenseShareList es " +
            "JOIN es.user us " +
            "WHERE g.id = ?1 " +
            "GROUP BY g.groupName, e.description, u.username, us.username")
    List<ReportDTO> generateReportById(UUID groupId);
}
