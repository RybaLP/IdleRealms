package com.social.service.application.service;


import com.social.service.domain.model.Guild;
import com.social.service.domain.model.Message;
import com.social.service.domain.model.Player;
import com.social.service.domain.port.in.*;
import com.social.service.domain.port.out.GuildRepository;
import com.social.service.domain.port.out.MessageRepository;
import com.social.service.domain.port.out.NotificationPort;
import com.social.service.domain.port.out.PlayerRepository;
import com.social.service.infrastructure.adapters.in.dto.GuildInvitationResponseDto;
import com.social.service.shared.GuildInvResponse;
import com.social.service.shared.MessageStatus;
import com.social.service.shared.MessageType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MessageService implements SendMessageUseCase, SendGuildInvitationUseCase, GetPlayerInboxUseCase, RemoveMessageUseCase, HandleGuildInvitationUseCase {

    private final MessageRepository messageRepository;
    private final PlayerRepository playerRepository;
    private final GuildRepository guildRepository;
    private final GuildService guildService;

//    web socket
    private final NotificationPort notificationPort;

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
        notificationPort.sendNotification(recipientId,"message");
    }

    @Override
    public void sendInvitation(UUID ownerSocialId, UUID recipientSocialId, UUID guildId) {

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
        notificationPort.sendNotification(recipientSocialId, java.util.Map.of(
                "subject", "Guild Invitation",
                "content", "You were invited to the guild: " + guild.getName()
        ));
    }

    @Transactional
    public void removeMessage (UUID id, UUID recipientId) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + id));

        if (!message.getRecipientId().equals(recipientId)) {
            throw new SecurityException("You are not allowed to delete this message!");
        }
        messageRepository.deleteByIdAndRecipientId(id,recipientId);
    }

    @Transactional
    public void handleGuildInvitation(UUID messageId, UUID recipientId, GuildInvitationResponseDto guildInvitationResponse) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Invitation not found"));

        if (!message.getRecipientId().equals(recipientId)) {
            throw new SecurityException("This invitation does not belong to you!");
        }

        if (message.getType() != MessageType.GUILD_INVITATION) {
            throw new IllegalArgumentException("Selected message is not a guild invitation");
        }

        if (message.getStatus() == MessageStatus.CANCELLED) {
            throw new IllegalArgumentException("Invitation is no longer valid");
        }

        if (guildInvitationResponse.action().equals(GuildInvResponse.ACCEPT)) {
            guildService.addMember(message.getReferenceId(), recipientId);
        }

        messageRepository.deleteByIdAndRecipientId(messageId,recipientId);
    }

}
