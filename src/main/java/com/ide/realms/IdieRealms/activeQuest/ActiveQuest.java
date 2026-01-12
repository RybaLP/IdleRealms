package com.ide.realms.IdieRealms.activeQuest;

import com.ide.realms.IdieRealms.hero.Hero;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "hero_id")
    private Hero hero;

    private Long monsterId;
    private double difficultyMultiplier;

    private int goldReward;
    private int expReward;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;

//    ui
    @Builder.Default
    private boolean completed = false;

    @Builder.Default
    private boolean rewardsClaimed = false;
}