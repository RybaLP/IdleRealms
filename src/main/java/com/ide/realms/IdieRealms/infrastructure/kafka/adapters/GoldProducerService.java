package com.ide.realms.IdieRealms.infrastructure.kafka.adapters;

import com.ide.realms.IdieRealms.infrastructure.kafka.dto.PlayerGoldEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoldProducerService {

    private final KafkaTemplate<String, PlayerGoldEvent> kafkaTemplate;
    private static final String TOPIC = "player-gold-events";

    public void sendGoldEarned(String playerId, Long amount) {
        PlayerGoldEvent playerGoldEvent = new PlayerGoldEvent(playerId,amount);
        log.info("Sending gold event to Kafka: {}", playerGoldEvent);
        kafkaTemplate.send(TOPIC,playerGoldEvent);
    }

}
