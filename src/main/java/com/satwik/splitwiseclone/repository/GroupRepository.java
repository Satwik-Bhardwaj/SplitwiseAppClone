package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByUserId (int userId);
}
