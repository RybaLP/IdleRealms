package com.social.service.infrastructure.adapters.out.persistance.mappers;

import com.social.service.domain.model.Player;
import com.social.service.infrastructure.adapters.out.persistance.entity.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "cachedAt", expression = "java(java.time.LocalDateTime.now())")
    PlayerEntity toEntity(Player player);

    Player toDomain(PlayerEntity playerEntity);
}
