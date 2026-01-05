package com.ide.realms.IdieRealms.hero;

import com.ide.realms.IdieRealms.shared.HeroClass;
import jakarta.persistence.*;
import lombok.*;

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

//    methods
    public void levelUp () {
        this.level ++;
        this.strength ++;
        this.intelligence ++;
        this.constitution ++;
        this.luck ++;
    }
}