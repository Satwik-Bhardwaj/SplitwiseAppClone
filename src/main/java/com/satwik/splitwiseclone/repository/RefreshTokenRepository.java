package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.models.RefreshToken;
import com.satwik.splitwiseclone.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    RefreshToken findByToken(String token);

    RefreshToken findByUser(User user);
}
