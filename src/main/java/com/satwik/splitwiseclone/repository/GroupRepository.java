package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.dto.report.TempReport;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.persistence.models.Group;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findByUserId (UUID userId);

    @Query(value = "SELECT * FROM group_table g WHERE g.default_group = TRUE AND g.user_id = ?1", nativeQuery = true)
    Optional<Group> findDefaultGroup(UUID userId);

    @Query("SELECT NEW com.satwik.splitwiseclone.persistence.dto.report.TempReport("+
            "e.id, " +
            "g.groupName, " +
            "e.description, " +
            "e.owner.username, " +
            "e.amount) " +
            "FROM Expense e " +
            "JOIN e.group g " +
            "WHERE g.id = ?1 ")
    List<TempReport> generateReportById(UUID groupId);

//    @Query("SELECT NEW com.satwik.splitwiseclone.persistence.dto.user.UserDTO("+
//            "m.username, m.email, m.countryCode, m.phoneNumber) FROM Group g JOIN g.members m WHERE g.id = ?1")
//    List<UserDTO> findMembersOfGroupById(UUID groupId);
//
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM group_members gm WHERE gm.group_id = :groupId AND gm.member_id = :memberId", nativeQuery = true)
////    @Query("DELETE FROM group_members m JOIN Group g ON g.id = m.group_id JOIN User u ON u.id = m.member_id WHERE m.group_id = :groupId AND m.member_id = :memberId")
//    void removeMemberFromGroupById(@Param("groupId") UUID groupId, @Param("memberId") UUID memberId);
}
