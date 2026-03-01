package com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka;

import java.util.UUID;

public interface GuildEventPublisher {
    void depositGold (UUID socialid, long amount);
}
