package com.ide.realms.IdieRealms.item.dto;

import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.ItemType;

public record ItemResponseDto(
        Long id,
        String name,
        String imageUrl,
        ItemType itemType,
        HeroClass heroClass,

        int strengthBonus,

        int power,
        int dexterityBonus,
        int intelligenceBonus,
        int constitutionBonus,
        int luckBonus,

        int price,
        int requiredLevel
){}