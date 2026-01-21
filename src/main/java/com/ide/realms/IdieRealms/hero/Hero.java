package com.ide.realms.IdieRealms.hero;

import com.ide.realms.IdieRealms.hero.dto.BaseStatsDto;
import com.ide.realms.IdieRealms.hero.dto.HeroFinalStatsDto;
import com.ide.realms.IdieRealms.item.Item;
import com.ide.realms.IdieRealms.shared.DamageResult;
import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.ItemType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    class
    @Enumerated(EnumType.STRING)
    private HeroClass heroClass;

//    info

    @Column(unique = true, nullable = false)
    private String nickname;

    @Builder.Default
    private int level = 1;

    @Builder.Default
    private long experience = 0;

    @Builder.Default
    private int gold = 10;

//    stats
    @Builder.Default
    private int strength = 10;

    @Builder.Default
    private int dexterity = 10;

    @Builder.Default
    private int intelligence = 10;

    @Builder.Default
    private int constitution = 10;

    @Builder.Default
    private int luck = 10;

//    character look
    @Builder.Default
    private String visualConfig = "0;0;0;0;0";


//    inventory
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hero_id")
    private List<Item> inventory = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "weapon_id")
    private Item equippedWeapon;

    @OneToOne
    @JoinColumn(name = "helmet_id")
    private Item equippedHelmet;

    @OneToOne
    @JoinColumn(name = "armor_id")
    private Item equippedArmor;

    @OneToOne
    @JoinColumn(name = "gloves_id")
    private Item equippedGloves;

    @OneToOne
    @JoinColumn(name = "boots_id")
    private Item equippedBoots;


//    bonus statisctics from items
    private int bonusStrength;
    private int bonusDexterity;
    private int bonusIntelligence;
    private int bonusConstitution;
    private int bonusLuck;

    private int totalArmor;


//    tavern
    @Builder.Default
    private int energy = 100;

    private LocalDateTime questFinishTime;


//    methods

//    exp
    public void levelUp () {
        this.level ++;
        this.strength ++;
        this.intelligence ++;
        this.constitution ++;
        this.luck ++;
    }

    public int calculateExpNeeded () {
        return 500 * (this.level * this.level);
    }

    public void addExperience (int amountOfExp) {
        this.experience += amountOfExp;
        while (this.experience >= calculateExpNeeded()) {
            levelUp();
        }
    }

// battle
    public int calculateFullHp () {
        int multiplier = switch (this.getHeroClass()) {
            case WARRIOR -> 5;
            case SCOUT -> 3;
            case MAGE -> 2;
        };

        return this.getConstitution() * multiplier * (this.getLevel() + 1);
    }

    public DamageResult calculateDamage() {

        var didCrit = false;

        int damageStat = switch (this.heroClass) {
            case WARRIOR -> this.strength + bonusStrength;
            case SCOUT -> this.dexterity + bonusDexterity;
            case MAGE -> this.intelligence + bonusIntelligence;
        };

        int weaponPower = (equippedWeapon != null) ? this.equippedWeapon.getPower() : 0;

//        calculates average damage
        double averageDamage = (damageStat / 2.0) + weaponPower;

//        randomize atack damage
        double randomness = 0.8 + (Math.random() * 0.4);

        int finalDamage = (int) (averageDamage * randomness);

//        crit damage chance
        if (Math.random() * 100 < this.luck + this.bonusLuck) {
            finalDamage *= 2;
            didCrit = true;
        }

        return new DamageResult(Math.max(1,finalDamage) , didCrit);
    }

    private void applyItemStats (Item item) {

        if (item != null) {
            this.bonusStrength += item.getStrengthBonus();
            this.bonusDexterity += item.getDexterityBonus();
            this.bonusIntelligence += item.getIntelligenceBonus();
            this.bonusConstitution += item.getConstitutionBonus();
            this.bonusLuck += item.getLuckBonus();


            if (item.getItemType() != ItemType.WEAPON) {
                this.totalArmor += item.getPower();
            }
        }
    }

    public void refreshBonuses () {
        this.bonusStrength = 0;
        this.bonusDexterity = 0;
        this.bonusIntelligence = 0;
        this.bonusConstitution = 0;
        this.bonusLuck = 0;
        this.totalArmor = 0;

        applyItemStats(equippedWeapon);
        applyItemStats(equippedHelmet);
        applyItemStats(equippedArmor);
        applyItemStats(equippedGloves);
        applyItemStats(equippedBoots);
    }

    public HeroFinalStatsDto getFinalStatistics () {
        refreshBonuses();
        return new HeroFinalStatsDto(
                this.strength + this.bonusStrength,
                this.dexterity + this.bonusDexterity,
                this.intelligence + this.bonusIntelligence,
                this.constitution + this.bonusConstitution,
                this.luck + this.bonusLuck,
                this.totalArmor,
                this.calculateFullHp(),
                this.level
        );
    }

    public BaseStatsDto getBaseStats () {
        return new BaseStatsDto(this.strength,this.dexterity,this.intelligence,this.constitution,this.luck);
    }
}