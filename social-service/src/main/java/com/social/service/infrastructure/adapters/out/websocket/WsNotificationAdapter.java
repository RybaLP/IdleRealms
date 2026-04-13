package com.social.service.infrastructure.adapters.out.websocket;

import com.social.service.domain.port.out.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WsNotificationAdapter implements NotificationPort {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendNotification(UUID recipientId, Object notification) {
        String destination = "/topic/notifications." + recipientId;
        simpMessagingTemplate.convertAndSend(destination, notification);
    }
}
