package com.social.service.domain.port.in;

import com.social.service.infrastructure.adapters.in.dto.GuildDetailsDto;

import java.util.UUID;

public interface GetPlayerGuildUseCase {
    GuildDetailsDto getPlayerGuild(UUID socialId);
}
