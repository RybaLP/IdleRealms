package com.social.service.infrastructure.adapters.out.persistance.repository;

import com.social.service.infrastructure.adapters.out.persistance.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, UUID> {
    Optional<PlayerEntity> findBySocialId (UUID socialId);
}
