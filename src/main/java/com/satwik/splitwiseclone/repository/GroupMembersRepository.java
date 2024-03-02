package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.models.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupMembersRepository extends JpaRepository<GroupMembers, UUID> {

    List<GroupMembers> findByGroupId(UUID id);

}
