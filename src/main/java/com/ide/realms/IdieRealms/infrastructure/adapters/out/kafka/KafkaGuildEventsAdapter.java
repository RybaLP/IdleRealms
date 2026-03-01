package com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka;


import com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka.dto.PlayerGoldEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaGuildEventsAdapter implements GuildEventPublisher {

    private final KafkaTemplate<String,Object> kafkaTemplate;
    private static final String GOLD_DEPOSIT_TOPIC = "player-gold-events";

    @Override
    public void depositGold(UUID socialid, long amount) {

        if (amount <= 0) {
            log.warn("Amount of gold must be greater than 0", socialid, amount);
            return;
        }

        PlayerGoldEvent playerGoldEvent = new PlayerGoldEvent(socialid,amount);

        try {
            log.info("Publishing gold event to Kafka. Player ID: {}, Amount: {}", socialid, amount);
            kafkaTemplate.send(GOLD_DEPOSIT_TOPIC,playerGoldEvent);
        } catch (Exception e) {
            log.error("Failed to send gold event to Kafka for Player ID: {}. Amount: {}. Error: {}",
                    socialid, amount, e.getMessage(), e);
            throw new RuntimeException("Unable to publish gold event", e);
        }

    }
}
