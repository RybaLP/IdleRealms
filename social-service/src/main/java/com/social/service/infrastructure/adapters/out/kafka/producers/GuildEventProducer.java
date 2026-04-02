package com.social.service.infrastructure.adapters.out.kafka.producers;

import com.social.service.domain.port.out.GuildNotificationPort;
import com.social.service.infrastructure.adapters.out.kafka.dto.GuildCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class GuildEventProducer implements GuildNotificationPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "player-guild-events";

    @Override
    public void notifyGuildCreated(UUID guildId, UUID ownerId, String name) {

        GuildCreateEvent guildCreateEvent = new GuildCreateEvent(guildId,ownerId,name);

        kafkaTemplate.send(TOPIC, guildId.toString(),guildCreateEvent)
                .whenComplete((result,ex) -> {
                   if (ex == null) {
                       log.info("Successfully sent GuildCreateEvent for guild: {} with id: {}", name, guildId);
                   } else {
                       log.error("Failed to send GuildCreateEvent for guild: {}. Error: {}", name, ex.getMessage());
                   }
                });
    }

}