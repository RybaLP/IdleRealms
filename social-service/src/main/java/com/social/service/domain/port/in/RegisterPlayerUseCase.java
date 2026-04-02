package com.social.service.domain.port.in;

import java.util.UUID;

public interface RegisterPlayerUseCase {
    void register (UUID socialid,String username);
}