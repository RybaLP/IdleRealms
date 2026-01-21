package com.ide.realms.IdieRealms.hero.dto;

import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;
import com.ide.realms.IdieRealms.shared.HeroClass;

import java.util.List;


public record HeroProfileResponse(
        String nickname,
        HeroClass heroClass,
        int level,
        long experience,
        int gold,
        int energy,
        String visualConfig,
        HeroFinalStatsDto stats,
        BaseStatsDto baseStats,

        List<ItemResponseDto> inventory,
        ItemResponseDto equippedHelmet,
        ItemResponseDto equippedWeapon,
        ItemResponseDto equippedArmor,
        ItemResponseDto equippedGloves,
        ItemResponseDto equippedBoots
){}