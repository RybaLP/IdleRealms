package com.social.service.domain.port.in;

import com.social.service.infrastructure.adapters.in.dto.GuildInvitationResponseDto;

import java.util.UUID;

public interface HandleGuildInvitationUseCase {
    void handleGuildInvitation(UUID messageId, UUID recipientId, GuildInvitationResponseDto guildInvitationResponse);
}
