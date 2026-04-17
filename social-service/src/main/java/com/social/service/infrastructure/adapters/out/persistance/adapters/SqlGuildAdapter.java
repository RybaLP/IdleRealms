package com.social.service.infrastructure.adapters.out.persistance.adapters;

import com.social.service.domain.model.Guild;
import com.social.service.domain.port.out.GuildRepository;
import com.social.service.infrastructure.adapters.out.persistance.entity.GuildEntity;
import com.social.service.infrastructure.adapters.out.persistance.mappers.GuildMapper;
import com.social.service.infrastructure.adapters.out.persistance.repository.GuildJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SqlGuildAdapter implements GuildRepository {

    private final GuildJpaRepository guildJpaRepository;
    private final GuildMapper guildMapper;

    @Override
    public void save(Guild guild) {
        GuildEntity entity = guildMapper.toEntity(guild);
        guildJpaRepository.save(entity);
    }

    @Override
    public Optional<Guild> findById(UUID id) {
        return guildJpaRepository.findById(id)
                .map(guildMapper::toDomain);
    }

    @Override
    public Optional<Guild> findByName(String name) {
        return guildJpaRepository.findByName(name)
                .map(guildMapper::toDomain);
    }

    @Override
    public List<String> findNicknamesBySocialIds(List<UUID> socialIds) {
        return guildJpaRepository.findUsernamesBySocialIds(socialIds);
    }

}
