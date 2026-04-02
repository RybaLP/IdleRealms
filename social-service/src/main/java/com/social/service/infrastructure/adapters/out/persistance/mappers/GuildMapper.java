package com.social.service.infrastructure.adapters.out.persistance.mappers;

import com.social.service.domain.model.Guild;
import com.social.service.infrastructure.adapters.out.persistance.entity.GuildEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuildMapper {
    GuildEntity toEntity (Guild guild);
    Guild toDomain(GuildEntity guildEntity);
}