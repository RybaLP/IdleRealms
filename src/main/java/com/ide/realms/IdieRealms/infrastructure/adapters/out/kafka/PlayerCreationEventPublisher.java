package com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka;

import java.util.UUID;

public interface PlayerCreationEventPublisher {
    void publishPlayerCreated (UUID socialId, String nickname);
}