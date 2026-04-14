package com.social.service.domain.port.in;

import java.util.UUID;

public interface SendMessageUseCase {
    void sendMessage (UUID senderId, String username, String topic, String content);
}