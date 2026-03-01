package com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka.events;

import java.util.UUID;

public record PlayerCreatedEvent(UUID socialId, String username) {}
