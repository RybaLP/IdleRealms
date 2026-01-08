package com.ide.realms.IdieRealms.battle.dto;

import com.ide.realms.IdieRealms.hero.dto.HeroFinalStatsDto;
import com.ide.realms.IdieRealms.monster.dto.MonsterFinalStatsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class PVEbattleResult {

    private List<BattleTurnDto> battleLogs;

    private String monsterName;
    private String monsterImageUrl;
    private String monsterWeaponUrl;

    private String heroNickname;
    private String heroImageUrl;
    private String heroWeaponUrl;

    private HeroFinalStatsDto heroFinalStatsDto;
    private MonsterFinalStatsDto monsterFinalStatsDto;

    private boolean hasPlayerWon;

    private int earnedExp;
    private int earnedGold;
}