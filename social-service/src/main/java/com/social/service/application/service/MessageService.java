package com.social.service.application.service;


import com.social.service.domain.model.Guild;
import com.social.service.domain.model.Message;
import com.social.service.domain.model.Player;
import com.social.service.domain.port.in.GetPlayerInboxUseCase;
import com.social.service.domain.port.in.SendGuildInvitationUseCase;
import com.social.service.domain.port.in.SendMessageUseCase;
import com.social.service.domain.port.out.GuildRepository;
import com.social.service.domain.port.out.MessageRepository;
import com.social.service.domain.port.out.PlayerRepository;
import com.social.service.infrastructure.adapters.in.dto.MessageDto;
import com.social.service.shared.MessageStatus;
import com.social.service.shared.MessageType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MessageService implements SendMessageUseCase, SendGuildInvitationUseCase, GetPlayerInboxUseCase {

    private final MessageRepository messageRepository;
    private final PlayerRepository playerRepository;
    private final GuildRepository guildRepository;

    @Override
    public List<Message> getInboxBySocialId (UUID socialId) {
        List<Message> messages = messageRepository.findByRecipientId(socialId);
        return messages != null ? messages : Collections.emptyList();
    }

    @Override
    public void sendMessage(UUID senderId, UUID recipientId, String topic, String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        Player recipient = playerRepository.findBySocialId(recipientId)
                .orElseThrow(() -> new EntityNotFoundException("Recipient with ID " + recipientId + " not found"));

        String finalSubject = (topic == null || topic.isBlank()) ? "No Title" : topic;

        Message message = new Message(
                UUID.randomUUID(),
                senderId,
                recipient.getSocialId(),
                finalSubject,
                content,
                MessageType.PRIVATE_MESSAGE,
                MessageStatus.PENDING,
                null,
                LocalDateTime.now()
        );

        messageRepository.save(message);
    }

    @Override
    public void sendInvitation(UUID ownerSocialId, UUID recipientSocialId, UUID guildId) {

        Player player = playerRepository.findBySocialId(recipientSocialId)
                .orElseThrow(() -> new EntityNotFoundException("Recipient with provided id does not exist"));

        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Guild with provided id does not exist"));

        if (ownerSocialId.equals(recipientSocialId)) {
            throw new IllegalArgumentException("Owner can not invite himself!");
        }

//        guild validation
        if (!guild.getOwnerSocialId().equals(ownerSocialId)) {
            throw new RuntimeException("Only owner can invite members to guild");
        }

        if (guild.getMemberSocialIds().size() >= 50) {
            throw new RuntimeException("Guild is already full");
        }

//
        String subject = "You were invited to the guild: " + guild.getName();


        Message message = new Message(
                UUID.randomUUID(),
                ownerSocialId,
                recipientSocialId,
                "Guild Invitation",
                subject,
                MessageType.GUILD_INVITATION,
                MessageStatus.PENDING,
                guildId,
                LocalDateTime.now()
        );

        messageRepository.save(message);
    }

}
