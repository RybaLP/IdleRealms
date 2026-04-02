package com.social.service.domain.port.in;

import java.util.UUID;

public interface CreateGuildUseCase {
    void createGuild (String name, UUID ownerSocialId);
}
