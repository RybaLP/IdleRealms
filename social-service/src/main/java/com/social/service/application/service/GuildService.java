package com.social.service.application.service;

import com.social.service.domain.model.Guild;
import com.social.service.domain.model.Player;
import com.social.service.domain.port.in.CreateGuildUseCase;
import com.social.service.domain.port.in.KickFromGuildUseCase;
import com.social.service.domain.port.in.LeaveGuildUseCase;
import com.social.service.domain.port.out.GuildRepository;
import com.social.service.domain.port.out.PlayerRepository;
import com.social.service.infrastructure.adapters.out.kafka.producers.GuildEventProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuildService implements CreateGuildUseCase, KickFromGuildUseCase, LeaveGuildUseCase {

    private final PlayerRepository playerRepository;
    private final GuildRepository guildRepository;
    private final GuildEventProducer guildEventProducer;


    @Override
    @Transactional
    public void createGuild(String name, UUID ownerSocialId) {

        Player player = playerRepository.findBySocialId(ownerSocialId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

        if (guildRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Provided guild name is taken");
        }

        if (player.getGuildId() != null) {
            throw new IllegalArgumentException("Player is already a member of a guild");
        }

        Guild guild = Guild.createNew(name,ownerSocialId);

        guildRepository.save(guild);

//        player update
        player.setGuildId(guild.getId());
        playerRepository.save(player);

        guildEventProducer.notifyGuildCreated(guild.getId(), guild.getOwnerSocialId(), guild.getName());
        log.info("Guild {} created by player {}", name, ownerSocialId);
    }

    @Transactional
    public void addMember(UUID guildId, UUID memberSocialId) {

        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Guild with provided id not found"));

        guild.addMember(memberSocialId);
        guildRepository.save(guild);
    }

    @Transactional
    @Override
    public void kickFromGuild (UUID ownerSocialId, String username, UUID guildId) {

        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find guild with provided id"));

        if (!guild.getOwnerSocialId().equals(ownerSocialId)) {
            throw new RuntimeException("Only the guild owner can kick members.");
        }

        Player player = playerRepository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException("Player with provided username not found"));

        UUID memberSocialid = player.getSocialId();

        guild.kickMember(memberSocialid);
        guildRepository.save(guild);
    }

    @Override
    @Transactional
    public void leave (UUID socialId, UUID guildId) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find guild with provided id"));

        guild.removePlayer(socialId);
        guildRepository.save(guild);
    }
}
