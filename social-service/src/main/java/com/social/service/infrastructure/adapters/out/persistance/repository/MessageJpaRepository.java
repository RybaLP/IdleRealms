package com.social.service.infrastructure.adapters.out.persistance.repository;

import com.social.service.infrastructure.adapters.out.persistance.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findByRecipientId (UUID recipientId);
    void deleteByIdAndRecipientId (UUID id, UUID recipientId);
    void deleteAllByRecipientId(UUID recipientId);

    @Modifying
    @Transactional
    @Query("UPDATE MessageEntity m SET m.status = 'CANCELLED' " +
            "WHERE m.recipientId = :recipientId AND m.referenceId = :guildId AND m.status = 'PENDING'")
    void cancelInvitations(
            @Param("guildId") UUID guildId,
            @Param("recipientId") UUID recipientId
    );
}