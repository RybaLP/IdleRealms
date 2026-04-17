package com.social.service.infrastructure.adapters.in.rest;

import com.social.service.application.service.GuildService;
import com.social.service.infrastructure.adapters.in.dto.GuildDetailsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/guild-chat")
public class GuildChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GuildService guildService;

    @PostMapping("/{guildId}")
    public ResponseEntity<Void> sendChatMessage(
            @PathVariable UUID guildId,
            @Valid @RequestBody GuildDetailsDto.ChatMessageDto request
    ) {
        GuildDetailsDto.ChatMessageDto message = new GuildDetailsDto.ChatMessageDto(
                request.sender(),
                request.content()
        );

        guildService.saveMessageToRam(guildId, message);

        simpMessagingTemplate.convertAndSend("/topic/guild/" + guildId, message);

        return ResponseEntity.ok().build();
    }

}