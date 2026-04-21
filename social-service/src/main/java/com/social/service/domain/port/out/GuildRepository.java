package com.social.service.domain.port.out;

import com.social.service.domain.model.Guild;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GuildRepository {
    void save (Guild guild);
    Optional<Guild> findById (UUID id);
    Optional<Guild> findByName(String name);
    List<String> findNicknamesBySocialIds (List<UUID> socialIds);
    void delete (Guild guild);
}
