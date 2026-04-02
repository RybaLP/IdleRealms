package com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka;

import com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka.events.PlayerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaPlayerCreationEventPublisher implements PlayerCreationEventPublisher{

    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Value("${kafka.topics.player-register}")
    private String TOPIC;

    @Override
    public void publishPlayerCreated(UUID socialId, String nickname) {
        try {
            log.info("Publishing HeroCreatedEvent to Kafka. Social ID: {}, Nickname: {}", socialId, nickname);
            PlayerCreatedEvent event = new PlayerCreatedEvent(socialId, nickname);
            kafkaTemplate.send(TOPIC, socialId.toString(), event);
        } catch (Exception e) {
            log.error("Failed to publish player registration event. Social ID: {}. Error: {}",
                    socialId, e.getMessage(), e);
            throw new RuntimeException("Kafka publish error", e);
        }
    }
}