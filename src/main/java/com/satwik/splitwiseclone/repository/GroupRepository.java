package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByUserId (int userId);
}
