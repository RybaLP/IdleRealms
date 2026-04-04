package com.social.service.infrastructure.adapters.out.persistance.entity;

import com.social.service.shared.MessageStatus;
import com.social.service.shared.MessageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class MessageEntity {

    @Id
    private UUID id;
    private UUID recipientId;
    private UUID senderId;
    private String subject;
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    private UUID referenceId;
    private LocalDateTime createdAt;

}