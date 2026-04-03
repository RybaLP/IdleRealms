package com.social.service.infrastructure.adapters.in.rest;

import com.social.service.application.service.GuildService;
import com.social.service.infrastructure.adapters.in.dto.CreateGuildRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guilds")
public class GuildController {

    private final GuildService guildService;

    @PostMapping
    public ResponseEntity<Void> createGuild (@Valid @RequestBody CreateGuildRequestDto createGuildRequestDto) {
        guildService.createGuild(createGuildRequestDto.name(), createGuildRequestDto.ownerSocialId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{guildId}/members/{memberId}/owner/{ownerId}")
    public ResponseEntity<Void> kickMember(
            @PathVariable UUID guildId,
            @PathVariable UUID memberId,
            @PathVariable UUID ownerId
    ) {
        guildService.kickFromGuild(ownerId, memberId, guildId);
        return ResponseEntity.noContent().build();
    }

}