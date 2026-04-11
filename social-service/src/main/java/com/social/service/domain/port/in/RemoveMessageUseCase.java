package com.social.service.domain.port.in;

import java.util.UUID;

public interface RemoveMessageUseCase {
    void removeMessage(UUID messageId, UUID recipientId);
}
