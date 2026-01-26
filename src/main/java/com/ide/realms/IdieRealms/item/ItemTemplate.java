package com.ide.realms.IdieRealms.item;

import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.ItemType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    private HeroClass heroClass;
}