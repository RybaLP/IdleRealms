package com.social.service.infrastructure.adapters.in.rest;

import com.social.service.application.service.MessageService;
import com.social.service.domain.model.Message;
import com.social.service.domain.port.in.GetPlayerInboxUseCase;
import com.social.service.domain.port.in.SendGuildInvitationUseCase;
import com.social.service.domain.port.in.SendMessageUseCase;
import com.social.service.infrastructure.adapters.in.dto.GuildInvitationRequestDto;
import com.social.service.infrastructure.adapters.in.dto.MessageDto;
import com.social.service.infrastructure.adapters.in.dto.MessageRequestDto;
import com.social.service.infrastructure.adapters.out.persistance.mappers.MessageMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final SendMessageUseCase sendMessageUseCase;
    private final SendGuildInvitationUseCase sendInvitationUseCase;
    private final GetPlayerInboxUseCase getInboxUseCase;
    private final MessageMapper messageMapper;


    @GetMapping("/{socialid}")
    public ResponseEntity<List<MessageDto>> getMessagesBySocialId (
            @PathVariable("socialid")UUID socialid) {
        List<Message> messages = getInboxUseCase.getInboxBySocialId(socialid);
        List<MessageDto> response = messageMapper.toDtoList(messages);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Void> sendMessage (@RequestBody @Valid MessageRequestDto messageRequestDto) {
        sendMessageUseCase.sendMessage(messageRequestDto.senderId(),messageRequestDto.recipientId(),messageRequestDto.topic(),messageRequestDto.content());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/guild-invitation")
    public ResponseEntity<Void> sendGuildInvitation (@RequestBody @Valid GuildInvitationRequestDto guildInvitationRequestDto) {
        sendInvitationUseCase.sendInvitation(guildInvitationRequestDto.ownerSocialId(), guildInvitationRequestDto.recipientSocialId(), guildInvitationRequestDto.guildId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
