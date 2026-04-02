package com.social.service.infrastructure.adapters.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guilds")
public class GuildEntity {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "owner_id")
    private UUID ownerSocialId;

    @Column(name = "total_gold")
    private int totalGold;

    @Column(name = "coach_level")
    private int coachLevel;

    @ElementCollection
    @CollectionTable(name = "guild_members", joinColumns = @JoinColumn(name = "guild_id"))
    @Column(name = "player_social_id")
    private List<UUID> memberSocialIds;

}