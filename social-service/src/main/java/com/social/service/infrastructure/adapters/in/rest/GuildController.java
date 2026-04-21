package com.social.service.infrastructure.adapters.in.rest;

import com.social.service.domain.port.in.*;
import com.social.service.infrastructure.adapters.in.dto.CreateGuildRequestDto;
import com.social.service.infrastructure.adapters.in.dto.GuildDetailsDto;
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
    private final GetPlayerGuildUseCase getPlayerGuildUseCase;
    private final DeleteGuildUseCase deleteGuildUseCase;

    @GetMapping("/player/{socialId}")
    public ResponseEntity<GuildDetailsDto> getGuildByPlayer (@PathVariable UUID socialId) {
        GuildDetailsDto guildDetailsDto = getPlayerGuildUseCase.getPlayerGuild(socialId);

        if (guildDetailsDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(guildDetailsDto);
    }

    @PostMapping
    public ResponseEntity<Void> createGuild (@Valid @RequestBody CreateGuildRequestDto createGuildRequestDto) {
        createGuildUseCase.createGuild(createGuildRequestDto.name(), createGuildRequestDto.ownerSocialId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{guildId}/members/{username}/owner/{ownerId}")
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

    @DeleteMapping("/{socialId}/{guildId}")
    public ResponseEntity<Void> deleteGuild(
            @PathVariable UUID guildId,
            @PathVariable UUID socialId) {
        deleteGuildUseCase.delete(guildId,socialId);
        return ResponseEntity.noContent().build();
    }

}