package com.social.service.domain.port.in;

import java.util.UUID;

public interface SendGuildMessageUseCase {
    void sendMessage (UUID guildId,UUID socialId,String content);
}
