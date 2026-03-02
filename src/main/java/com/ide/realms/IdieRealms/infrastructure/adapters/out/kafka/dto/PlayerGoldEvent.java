package com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka.dto;

import java.util.UUID;

public record PlayerGoldEvent(
        UUID socialId,long amount
){}