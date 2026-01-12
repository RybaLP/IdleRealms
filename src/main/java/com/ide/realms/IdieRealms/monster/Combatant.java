package com.ide.realms.IdieRealms.monster;


import com.ide.realms.IdieRealms.item.Item;
import com.ide.realms.IdieRealms.monster.dto.MonsterFinalStatsDto;
import com.ide.realms.IdieRealms.shared.DamageResult;
import com.ide.realms.IdieRealms.shared.HeroClass;

public interface Combatant {
    DamageResult calculateDamage();
    MonsterFinalStatsDto getFinalStats();
    HeroClass getHeroClass();
    String getName();
    String getImageUrl();
    Item getWeapon();
}