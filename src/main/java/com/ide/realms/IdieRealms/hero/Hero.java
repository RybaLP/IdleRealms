package com.ide.realms.IdieRealms.hero;

import com.ide.realms.IdieRealms.shared.HeroClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
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
    private int level = 1;
    private long experience = 0;
    private int gold = 10;

//    stats
    private int strength = 10;
    private int dexterity = 10;
    private int intelligence = 10;
    private int constitution = 10;
    private int luck = 10;


//    character look
    private String visualConfig = "0;0;0;0;0";


//    methods

    public void levelUp () {
        this.level ++;
        this.strength ++;
        this.intelligence ++;
        this.constitution ++;
        this.luck ++;
    }
}