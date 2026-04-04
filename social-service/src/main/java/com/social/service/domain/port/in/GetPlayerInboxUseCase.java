package com.social.service.domain.port.in;

import com.social.service.domain.model.Message;

import java.util.List;
import java.util.UUID;

public interface GetPlayerInboxUseCase {
    List<Message> getInboxBySocialId (UUID socialid);
}
