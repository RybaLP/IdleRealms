package com.social.service.domain.port.in;

import java.util.UUID;

public interface RemoveAllMessagesUseCase {
    void remove (UUID socialId);
}
