package com.ide.realms.IdieRealms.shop;

import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shop_id")
    @OrderColumn(name = "item_order")
    private List<Item> itemsInOffer = new ArrayList<>();

    @Builder.Default
    private int amountOfItems = 6;

    private LocalDateTime lastRefresh;
}