package com.social.service.infrastructure.adapters.out.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity {

    @Id
    private UUID socialId;

    @Column(nullable = false)
    private String username;

    @Builder.Default
    private LocalDateTime cachedAt = LocalDateTime.now();

    private UUID guildId;

    public PlayerEntity(UUID socialId, String username) {
        this.socialId = socialId;
        this.username = username;
        this.cachedAt = LocalDateTime.now();
    }
}