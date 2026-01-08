package com.ide.realms.IdieRealms.battle;

import com.ide.realms.IdieRealms.battle.dto.BattleTurnDto;
import com.ide.realms.IdieRealms.battle.dto.PVEbattleResult;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.monster.Monster;
import com.ide.realms.IdieRealms.shared.DamageResult;
import com.ide.realms.IdieRealms.shared.HeroClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleService {


    @Transactional
    public PVEbattleResult fightAgainstNpc (Hero hero, Monster monster) {

        List<BattleTurnDto> battleLogs = new ArrayList<>();

//        TODO
//        Replacer Those placeholders with imageurl of default weapon (fist)
        String heroWeaponUrl = (hero.getEquippedWeapon() != null) ? hero.getEquippedWeapon().getImageUrl() : "placeholder";
        String monsterWeaponUrl = (monster.getWeapon() != null) ? monster.getWeapon().getImageUrl() : "placeholder";

//        hero setup
        hero.refreshBonuses();
        var heroStatistics = hero.getFinalStatistics();
        var currentHeroHp = heroStatistics.maxHp();

//        monster setup
        var monsterStatistics = monster.getMonsterFinalStats();
        var currentMonsterHp = monsterStatistics.maxHp();

//        fight mechanic
        while (currentHeroHp > 0 && currentMonsterHp > 0) {
            DamageResult heroDamage = hero.calculateDamage();
            boolean didDodge = checkIfDodged(monster.getMonsterClass());

           if (!didDodge) {
//               hited atack
                int finalDamage = Math.max(1, heroDamage.damage() - (monsterStatistics.totalArmor() / 5));
                currentMonsterHp -= finalDamage;
                heroDamage = new DamageResult(finalDamage, heroDamage.isCrit());
                battleLogs.add(BattleTurnDto.builder()
                        .isDodge(false)
                        .heroAttacking(true)
                        .isCrit(heroDamage.isCrit())
                        .currentHeroHp(Math.max(0,currentHeroHp))
                        .currentMonsterHp(Math.max(0,currentMonsterHp))
                        .heroAttacking(true)
                        .damage(finalDamage)
                        .build()
                );
           }

           else {
//               missed atack
               battleLogs.add(BattleTurnDto.builder()
                       .isCrit(false)
                       .isDodge(true)
                       .currentHeroHp(currentHeroHp)
                        .currentMonsterHp(currentMonsterHp)
                       .heroAttacking(true)
                       .damage(0)
                       .build()
               );
           }

//            check whether monster was eliminated
            if (currentMonsterHp <= 0) break;
//
            DamageResult monsterDamage = monster.calculateDamage();
            var didHeroDodge = checkIfDodged(hero.getHeroClass());

            if (didHeroDodge) {
                battleLogs.add(BattleTurnDto.builder()
                        .isDodge(true)
                        .heroAttacking(false)
                        .damage(0)
                        .isCrit(false)
                        .currentHeroHp(currentHeroHp)
                        .currentMonsterHp(currentMonsterHp)
                        .build()
                );
            }

            else {
                int finalDamage = Math.max(1, monsterDamage.damage() - (heroStatistics.totalArmor() / 5));
                currentHeroHp -= finalDamage;
                battleLogs.add(BattleTurnDto.builder()
                        .isDodge(false)
                        .currentMonsterHp(currentMonsterHp)
                        .currentHeroHp(currentHeroHp)
                        .heroAttacking(false)
                        .isCrit(monsterDamage.isCrit())
                        .damage(finalDamage)
                        .build()
                );
            }
            if (currentHeroHp <= 0) break;
        }

        boolean heroWon = currentHeroHp > 0;
        int earnedExp = heroWon ? monster.getExpReward() : 0;
        int earnedGold = heroWon ? monster.getGoldReward() : 0;

        if (heroWon) {
            hero.setExperience(hero.getExperience() + earnedExp);
            hero.setGold(hero.getGold() + earnedGold);
        }

        return PVEbattleResult.builder()
                .battleLogs(battleLogs)
                .monsterWeaponUrl(monsterWeaponUrl)
                .heroWeaponUrl(heroWeaponUrl)
                .hasPlayerWon(heroWon)
                .earnedExp(earnedExp)
                .earnedGold(earnedGold)
                .monsterName(monster.getName())
                .monsterImageUrl(monster.getImageUrl())
                .heroNickname(hero.getNickname())
                .heroImageUrl(hero.getVisualConfig())
                .heroFinalStatsDto(heroStatistics)
                .monsterFinalStatsDto(monsterStatistics)
                .build();
    }


    private boolean checkIfDodged (HeroClass heroClass) {
        if (heroClass.equals(HeroClass.SCOUT)) {
            return Math.random() < 0.30;
        } else {
            return false;
        }
    }

}