package com.ide.realms.IdieRealms.activeQuest.dto;

import java.time.LocalDateTime;

public record ActiveQuestDto(
        String monsterName,
        String monsterImageUrl,
        int goldReward,
        int expReward,

        LocalDateTime startTime,
        LocalDateTime finishTime,

        long totalDurationSeconds,
        long secondsLeft,

        boolean completed,
        boolean rewardsClaimed
){}