package com.social.service.domain.port.out;

import com.social.service.domain.model.Message;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    void save(Message message);
    Optional<Message> findById(UUID id);
    List<Message> findByRecipientId(UUID recipientId);
    void deleteByIdAndRecipientId(UUID messageId, UUID recipientId);
}