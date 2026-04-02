package com.social.service.infrastructure.adapters.out.persistance.repository;

import com.social.service.infrastructure.adapters.out.persistance.entity.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuildJpaRepository extends JpaRepository<GuildEntity,UUID> {
    Optional<GuildEntity> findByName (String name);
}
