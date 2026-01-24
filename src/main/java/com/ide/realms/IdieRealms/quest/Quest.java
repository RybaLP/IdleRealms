package com.ide.realms.IdieRealms.quest;

import com.ide.realms.IdieRealms.hero.Hero;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private Hero hero;

    private Long monsterId;
    private String title;
    private String description;
    private String imageUrl;
    private int goldReward;
    private int expReward;
    private int durationInSeconds;
    private double difficulty;
    private int energyCost;

}
