package com.social.service.infrastructure.adapters.out.persistance.adapters;

import com.social.service.domain.model.Message;
import com.social.service.domain.port.out.MessageRepository;
import com.social.service.infrastructure.adapters.out.persistance.entity.MessageEntity;
import com.social.service.infrastructure.adapters.out.persistance.mappers.MessageMapper;
import com.social.service.infrastructure.adapters.out.persistance.repository.MessageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SqlMessageAdapter implements MessageRepository {

    private final MessageJpaRepository messageJpaRepository;
    private final MessageMapper messageMapper;

    @Override
    public void save(Message message) {
        MessageEntity messageEntity = messageMapper.toEntity(message);
        messageJpaRepository.save(messageEntity);
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return messageJpaRepository.findById(id)
                .map(messageMapper::toDomain);
    }

    @Override
    public List<Message> findByRecipientId(UUID recipientId) {
        return messageJpaRepository.findByRecipientId(recipientId)
                .stream()
                .map(messageMapper::toDomain)
                .collect(Collectors.toList());
    }
}