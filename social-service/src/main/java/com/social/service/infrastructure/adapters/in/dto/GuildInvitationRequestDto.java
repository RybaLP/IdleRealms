package com.social.service.infrastructure.adapters.in.dto;

import java.util.UUID;

public record GuildInvitationRequestDto(UUID ownerSocialId, UUID recipientSocialId, UUID guildId ) {
}
