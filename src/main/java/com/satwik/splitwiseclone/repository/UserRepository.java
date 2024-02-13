package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
