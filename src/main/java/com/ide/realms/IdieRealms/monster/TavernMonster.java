package com.ide.realms.IdieRealms.monster;

import com.ide.realms.IdieRealms.item.Item;
import com.ide.realms.IdieRealms.monster.dto.MonsterFinalStatsDto;
import com.ide.realms.IdieRealms.shared.DamageResult;
import com.ide.realms.IdieRealms.shared.HeroClass;

public class TavernMonster implements Combatant{

    private final Monster baseMonster;
    private final MonsterFinalStatsDto stats;
    private final HeroClass monsterClass;

    public TavernMonster (Monster base, int heroLevel, double diff){
        this.baseMonster = base;
        this.monsterClass = base.getMonsterClass();
        double scale = (1 + heroLevel * 0.15) * diff;

        int scaledHp = (int) (base.calculateFullHp() * scale);

        this.stats = new MonsterFinalStatsDto(
                (int)(base.getStrength() * scale),
                (int)(base.getDexterity() * scale),
                (int)(base.getIntelligence() * scale),
                (int)(base.getConstitution() * scale),
                (int)(base.getLuck() * scale),
                (int)(base.getTotalArmor() * scale),
                scaledHp,
                heroLevel,
                calculateGoldReward(heroLevel,diff),
                calculateExpReward(heroLevel,diff)
        );
    }

    private int calculateGoldReward(int heroLvl, double diff) {
        double baseGold = heroLvl * 15;
        double randomness = 0.9 + (Math.random() * 0.2);

        return (int) (baseGold * diff * randomness);
    }

    private int calculateExpReward(int heroLvl, double diff) {
        double baseExp = Math.pow(heroLvl, 1.2) * 60;

        double randomness = 0.95 + (Math.random() * 0.1);
        return (int) (baseExp * diff * randomness);
    }

    @Override
    public DamageResult calculateDamage() {
        var didCrit = false;

        int damageStat = switch (getHeroClass()) {
            case WARRIOR -> this.stats.strength();
            case SCOUT -> this.stats.dexterity();
            case MAGE -> this.stats.intelligence();
        };


        double averageDamage = (damageStat / 2.0);

        double randomness = 0.8 + (Math.random() * 0.4);

        int finalDamage = (int) (averageDamage * randomness);

//        crit chance
        if (Math.random() * 100 < this.stats.luck()) {
            finalDamage *= 2;
            didCrit = true;
        }

        return new DamageResult(Math.max(1,finalDamage) , didCrit);
    }

    @Override
    public MonsterFinalStatsDto getFinalStats() {
        return this.stats;
    }

    @Override
    public HeroClass getHeroClass() {
        return this.monsterClass;
    }

    @Override
    public String getName() {
        return baseMonster.getName();
    }

    @Override
    public String getImageUrl() {
        return baseMonster.getImageUrl();
    }

    @Override
    public Item getWeapon() {
        return this.baseMonster.getWeapon();
    }
}