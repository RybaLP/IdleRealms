package com.ide.realms.IdieRealms.hero.dto;

public record HeroFinalStatsDto(
        int totalStrength,
        int totalDexterity,
        int totalIntelligence,
        int totalConstitution,
        int totalLuck,
        int totalArmor,
        int maxHp,
        int level
) {}