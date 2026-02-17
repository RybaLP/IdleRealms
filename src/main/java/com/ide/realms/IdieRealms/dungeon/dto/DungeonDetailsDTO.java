package com.ide.realms.IdieRealms.dungeon.dto;

public record DungeonDetailsDTO(
        String dungeonName,
        int currentFloor,
        String floorDescription,
        String monsterName,
        String monsterImageUrl,
        int monsterLvl
){}