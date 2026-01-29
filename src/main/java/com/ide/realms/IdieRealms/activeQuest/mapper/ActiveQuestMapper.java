package com.ide.realms.IdieRealms.activeQuest.mapper;

import com.ide.realms.IdieRealms.activeQuest.ActiveQuest;
import com.ide.realms.IdieRealms.activeQuest.dto.ActiveQuestDto;
import com.ide.realms.IdieRealms.monster.Monster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Duration;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring" , imports = {LocalDateTime.class, Duration.class})
public interface ActiveQuestMapper {


    @Mapping(target = "monsterName", source = "monster.name")
    @Mapping(target = "monsterImageUrl", source = "monster.imageUrl")
    @Mapping(target = "goldReward", source = "activeQuest.goldReward")
    @Mapping(target = "expReward", source = "activeQuest.expReward")

    @Mapping(target = "secondsLeft", expression = "java(calculateSecondsLeft(activeQuest.getFinishTime()))")
    @Mapping(target = "totalDurationSeconds", expression = "java(Duration.between(activeQuest.getStartTime(), activeQuest.getFinishTime()).toSeconds())")

    @Mapping(target = "imageUrl", source = "activeQuest.imageUrl")
    @Mapping(target = "title", source = "activeQuest.title")
    ActiveQuestDto isActiveQuestDto (ActiveQuest activeQuest, Monster monster);

    default long calculateSecondsLeft(LocalDateTime finishTime) {
        if (finishTime == null) return 0;
        long seconds = Duration.between(LocalDateTime.now(), finishTime).toSeconds();
        return Math.max(0, seconds);
    }
}