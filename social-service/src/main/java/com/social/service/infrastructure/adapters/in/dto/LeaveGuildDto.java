package com.social.service.infrastructure.adapters.in.dto;

import java.util.UUID;

public record LeaveGuildDto(
        UUID socialId,
        UUID guildId
){}