package com.social.service.infrastructure.adapters.out.persistance.repository;

import com.social.service.infrastructure.adapters.out.persistance.entity.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuildJpaRepository extends JpaRepository<GuildEntity,UUID> {
    Optional<GuildEntity> findByName (String name);

    @Query("SELECT p.username FROM PlayerEntity p WHERE p.socialId IN :socialIds")
    List<String> findUsernamesBySocialIds(@Param("socialIds") List<UUID> socialIds);
}
