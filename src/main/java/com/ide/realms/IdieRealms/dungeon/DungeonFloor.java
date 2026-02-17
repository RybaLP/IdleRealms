package com.ide.realms.IdieRealms.dungeon;

import com.ide.realms.IdieRealms.monster.Monster;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DungeonFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dungeon_id")
    private Dungeon dungeon;

    @OneToOne
    @JoinColumn(name = "monster_id")
    private Monster monster;

    private int floorNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

}