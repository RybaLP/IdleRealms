package com.social.service.infrastructure.adapters.out.persistance.mappers;

import com.social.service.domain.model.Message;
import com.social.service.infrastructure.adapters.in.dto.MessageDto;
import com.social.service.infrastructure.adapters.out.persistance.entity.MessageEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toDomain(MessageEntity messageEntity);
    MessageEntity toEntity (Message message);
    MessageDto toDto (Message message);
    List<MessageDto> toDtoList (List<Message> messageList);
}