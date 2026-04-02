package com.social.service.infrastructure.adapters.out.kafka.dto;

import java.util.UUID;

public record GuildCreateEvent (
        UUID id,
        UUID ownerSocialId,
        String name
){}