package com.social.service.domain.port.in;

import java.util.UUID;

public interface KickFromGuildUseCase {
    void kickFromGuild (UUID ownerSocialId, UUID memberSocialId, UUID guildId);
}
