package com.social.service.infrastructure.adapters.in.dto;

import com.social.service.shared.MessageStatus;
import com.social.service.shared.MessageType;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageDto(
        UUID id,
        UUID senderId,
        String subject,
        String content,
        MessageType type,
        MessageStatus status,
        UUID referenceId,
        LocalDateTime createdAt
) {}