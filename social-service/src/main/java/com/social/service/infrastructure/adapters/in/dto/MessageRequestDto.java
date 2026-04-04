package com.social.service.infrastructure.adapters.in.dto;

import java.util.UUID;

public record MessageRequestDto(UUID senderId, UUID recipientId, String topic, String content){}