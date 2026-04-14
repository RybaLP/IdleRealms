package com.ide.realms.IdieRealms.item;

import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.ItemType;
import com.ide.realms.IdieRealms.shop.Shop;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String name;

    @Enumerated(EnumType.STRING)
    private HeroClass heroClass;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

//  stats
    @Builder.Default
    private int strengthBonus = 0;

    @Builder.Default
    private int dexterityBonus = 0;

    @Builder.Default
    private int intelligenceBonus = 0;

    @Builder.Default
    private int constitutionBonus = 0;

    @Builder.Default
    private int luckBonus = 0;

//  for weapon it gives damage, for equipment it gives armor
    private int power;

//    buy requirements
    private int price;
    private int requiredLevel;
}