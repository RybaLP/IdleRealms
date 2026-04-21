package com.social.service.infrastructure.adapters.in.dto;

import java.util.List;
import java.util.UUID;

public record GuildDetailsDto(
        UUID id,
        String name,
        int coachLevel,
        long totalGold,
        boolean isOwner,
        List<GuildMemberDto> members,
        List<ChatMessageDto> history
) {
    public record GuildMemberDto(String username) {}
    public record ChatMessageDto (String sender, String content) {}
}