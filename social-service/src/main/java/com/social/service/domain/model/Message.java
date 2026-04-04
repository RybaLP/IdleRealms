package com.social.service.domain.model;

import com.social.service.shared.MessageStatus;
import com.social.service.shared.MessageType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final UUID senderId;
    private final UUID recipientId;
    private final String subject;
    private final String content;
    private final MessageType type;
    private MessageStatus status;
    private final UUID referenceId;
    private final LocalDateTime createdAt;

    public Message(UUID id, UUID senderId, UUID recipientId, String subject, String content,
                   MessageType type, MessageStatus status, UUID referenceId, LocalDateTime createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.status = status;
        this.referenceId = referenceId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public String getSubject() {
        return subject;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public MessageStatus getStatus() {
        return status;
    }
}