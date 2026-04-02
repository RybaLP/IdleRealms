package com.social.service.infrastructure.adapters.in.kafka.dto;

import java.util.UUID;

public record PlayerCreatedEvent(
        UUID socialId,
        String username
){}