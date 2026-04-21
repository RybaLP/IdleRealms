package com.social.service.application.service.events;

import java.util.UUID;

public record CancelInvitationsEvent(
        UUID guildId,
        UUID recipientId
){}