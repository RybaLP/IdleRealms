package com.ide.realms.IdieRealms.shop;

import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemsInOffer;

    @Builder.Default
    private int amountOfItems = 6;

    private LocalDateTime lastRefresh;
}