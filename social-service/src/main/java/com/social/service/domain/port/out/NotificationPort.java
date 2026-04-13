package com.social.service.domain.port.out;

import java.util.UUID;

public interface NotificationPort {
    void sendNotification (UUID recipientId, Object notification);
}

