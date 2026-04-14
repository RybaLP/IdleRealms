package com.social.service.infrastructure.adapters.in.rest;

import com.social.service.application.service.GuildService;
import com.social.service.domain.port.in.CreateGuildUseCase;
import com.social.service.domain.port.in.KickFromGuildUseCase;
import com.social.service.domain.port.in.LeaveGuildUseCase;
import com.social.service.infrastructure.adapters.in.dto.CreateGuildRequestDto;
import com.social.service.infrastructure.adapters.in.dto.LeaveGuildDto;
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

    private final CreateGuildUseCase createGuildUseCase;
    private final KickFromGuildUseCase kickFromGuildUseCase;
    private final LeaveGuildUseCase leaveGuildUseCase;


    @PostMapping
    public ResponseEntity<Void> createGuild (@Valid @RequestBody CreateGuildRequestDto createGuildRequestDto) {
        createGuildUseCase.createGuild(createGuildRequestDto.name(), createGuildRequestDto.ownerSocialId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{guildId}/members/{memberId}/owner/{ownerId}")
    public ResponseEntity<Void> kickMember(
            @PathVariable UUID guildId,
            @PathVariable String username,
            @PathVariable UUID ownerId
    ) {
        kickFromGuildUseCase.kickFromGuild(ownerId, username, guildId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/leave")
    public ResponseEntity<Void> leaveGuild(@RequestBody LeaveGuildDto leaveGuildDto){
        leaveGuildUseCase.leave(leaveGuildDto.socialId(),leaveGuildDto.guildId());
        return ResponseEntity.noContent().build();
    }

}