package com.social.service.domain.model;

import java.util.UUID;

public class Player {

    private final UUID socialId;
    private final String username;
    private final UUID guildId;

    public Player(UUID socialId, String username) {
        this.socialId = socialId;
        this.username = username;
        this.guildId = null;
    }

    public UUID getSocialId() {
        return socialId;
    }

    public String getUsername() {
        return username;
    }

    public UUID getGuildId() {
        return guildId;
    }


}
