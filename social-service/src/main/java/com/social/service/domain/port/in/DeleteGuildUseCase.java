package com.social.service.domain.port.in;

import java.util.UUID;

public interface DeleteGuildUseCase {
    void delete (UUID guildId, UUID ownerId);
}
