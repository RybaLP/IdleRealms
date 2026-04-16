package com.social.service.domain.port.in;

import java.util.UUID;

public interface SendGuildInvitationUseCase {
    void sendInvitation(UUID ownerSocialId, String recipientUsername, UUID guildId);
}
