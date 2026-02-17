package com.ide.realms.IdieRealms.dungeon;

import com.ide.realms.IdieRealms.hero.Hero;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HeroDungeonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @ManyToOne
    @JoinColumn(name = "dungeon_id")
    private Dungeon dungeon;

    @Builder.Default
    private int completedFloors = 0;

    private boolean isCompleted;

}