package com.social.service.domain.port.out;

import com.social.service.domain.model.Player;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    void save (Player player);
    Optional<Player> findBySocialId (UUID socialId);
    Optional<Player> findByUsername(String nickname);
}