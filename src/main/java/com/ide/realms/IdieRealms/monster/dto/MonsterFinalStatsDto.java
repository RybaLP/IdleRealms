package com.ide.realms.IdieRealms.monster.dto;

public record MonsterFinalStatsDto(
        int strength,
        int dexterity,
        int intelligence,
        int constitution,
        int luck,
        int totalArmor,
        int maxHp,
        int level
) {}