package com.social.service.infrastructure.adapters.out.persistance.adapters;

import com.social.service.domain.model.Player;
import com.social.service.domain.port.out.PlayerRepository;
import com.social.service.infrastructure.adapters.out.persistance.entity.PlayerEntity;
import com.social.service.infrastructure.adapters.out.persistance.mappers.PlayerMapper;
import com.social.service.infrastructure.adapters.out.persistance.repository.PlayerJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SqlPlayerAdapter implements PlayerRepository {

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerMapper playerMapper;

    @Override
    public void save(Player player) {
        PlayerEntity playerEntity = playerMapper.toEntity(player);
        playerJpaRepository.save(playerEntity);
    }

    @Override
    public Optional<Player> findBySocialId(UUID socialId) {
        return playerJpaRepository.findBySocialId(socialId)
                .map(playerMapper::toDomain);
    }
}
