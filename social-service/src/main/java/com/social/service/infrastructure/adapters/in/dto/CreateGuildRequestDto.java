package com.social.service.infrastructure.adapters.in.dto;

import java.util.UUID;

public record CreateGuildRequestDto(
        UUID ownerSocialId,
        String name
){}