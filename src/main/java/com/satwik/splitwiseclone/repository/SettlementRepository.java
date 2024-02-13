package com.satwik.splitwiseclone.repository;

import com.satwik.splitwiseclone.persistence.models.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Integer> {
}
