package com.social.service.domain.port.in;

import java.util.UUID;

public interface LeaveGuildUseCase {
    void leave (UUID socialId, UUID guildId);
}