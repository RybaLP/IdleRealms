package com.ide.realms.IdieRealms.quest.dto;

public record QuestOfferDto(
        Long monsterId,
        String title,
        String description,
        String imageUrl,
        int goldReward,
        int expReward,
        int durationInSeconds,
        double difficulty
){}