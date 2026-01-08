package com.ide.realms.IdieRealms.battle.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BattleTurnDto {
    private boolean heroAttacking;
    private int damage;

    private boolean isCrit;
    private boolean isDodge;

    private int currentHeroHp;
    private int currentMonsterHp;
}