package com.social.service.infrastructure.adapters.out.persistance.repository;

import com.social.service.infrastructure.adapters.out.persistance.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findByRecipientId (UUID recipientId);
    void deleteByIdAndRecipientId (UUID id, UUID recipientId);
    void deleteAllByRecipientId(UUID recipientId);
}