package com.ide.realms.IdieRealms.dungeon;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dungeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private String imageUrl;

    @OneToMany(mappedBy = "dungeon", cascade = CascadeType.ALL)
    @OrderBy("floorNumber ASC")
    private List<DungeonFloor> floors;
}