package com.ide.realms.IdieRealms.monster;

import com.ide.realms.IdieRealms.item.Item;
import com.ide.realms.IdieRealms.monster.dto.MonsterFinalStatsDto;
import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.MonsterType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    class
    @Enumerated(EnumType.STRING)
    private HeroClass monsterClass;

//    visual
    private String name;
    private String imageUrl;

//    stats
    private int level;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int constitution;
    private int luck;

    private int totalArmor;

//    rewards
    private int expReward;
    private int goldReward;

//    weapon
    @OneToOne
    @JoinColumn(name = "weapon_id")
    private Item weapon;

//    type
    @Enumerated(EnumType.STRING)
    private MonsterType monsterType;

    public int calculateFullHp () {
        int multiplier = switch (this.getMonsterClass()) {
            case WARRIOR -> 5;
            case SCOUT -> 3;
            case MAGE -> 2;
        };

        return this.getConstitution() * multiplier * (this.getLevel() + 1);
    }

    public int calculateDamage () {
        int damageStat = switch (this.getMonsterClass()) {
            case WARRIOR -> this.strength;
            case SCOUT -> this.dexterity;
            case MAGE -> this.intelligence;
        };

        int weaponDamage = (this.weapon != null) ? this.weapon.getPower() : 0;

        double averageDamage = (damageStat / 2.0) + weaponDamage;

        double randomness = 0.8 + (Math.random() * 0.4);

        int finalDamage = (int) (averageDamage * randomness);

//        crit chance
        if (Math.random() * 100 < this.luck) {
            finalDamage *= 2;
        }

        return Math.max(1,finalDamage);
    }

    public MonsterFinalStatsDto getMonsterFinalStats () {
        return new MonsterFinalStatsDto(
                this.strength,
                this.dexterity,
                this.intelligence,
                this.constitution,
                this.luck,
                this.totalArmor,
                this.calculateFullHp(),
                this.level
        );
    }
}