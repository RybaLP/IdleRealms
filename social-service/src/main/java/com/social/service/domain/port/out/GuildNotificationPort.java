package com.social.service.domain.port.out;

import java.util.UUID;

public interface GuildNotificationPort {
    void notifyGuildCreated(UUID guildId, UUID ownerId, String name);
}