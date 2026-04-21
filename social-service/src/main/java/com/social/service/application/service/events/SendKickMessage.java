package com.social.service.application.service.events;

import java.util.UUID;

public record SendKickMessage(
        UUID senderId, String recipientUsername , String topic, String content
){}