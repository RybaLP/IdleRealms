package com.social.service.infrastructure.adapters.in.kafka.consumers;

import com.social.service.domain.port.in.RegisterPlayerUseCase;
import com.social.service.infrastructure.adapters.in.kafka.dto.PlayerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerEventConsumer {

    private final RegisterPlayerUseCase registerPlayerUseCase;

    private static final String TOPIC = "player-register-event";
    private static final String GROUP_ID = "social-service-player-registration";

    @KafkaListener(topics = TOPIC,groupId = GROUP_ID)
    public void consume (PlayerCreatedEvent playerCreatedEvent) {
        log.info("Received player registration event: socialId={}, username={}",
                playerCreatedEvent.socialId(), playerCreatedEvent.username());
        try {
            registerPlayerUseCase.register(playerCreatedEvent.socialId(), playerCreatedEvent.username());
        } catch (Exception e) {
            log.error("Failed to process player registration for socialId: {}. Error: {}",
                    playerCreatedEvent.socialId(), e.getMessage());
            throw new IllegalStateException("Error during player event consumption", e);
        }

    }

}