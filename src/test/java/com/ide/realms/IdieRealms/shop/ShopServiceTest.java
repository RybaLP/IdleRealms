package com.ide.realms.IdieRealms.shop;

import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.item.Item;
import com.ide.realms.IdieRealms.item.ItemService;
import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;
import com.ide.realms.IdieRealms.item.mapper.ItemMapper;
import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.ItemType;
import com.ide.realms.IdieRealms.shop.dto.ShopResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Shop Service unit tests")
class ShopServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ShopService shopService;

    private String email;
    private Hero hero;
    private Shop shop;
    private Account account;
    private List<Item> itemsInOffer;
    private List<ItemResponseDto> itemsResponse;


    @BeforeEach
    void setUp() {
        this.email = "test_email@example.com";

        this.hero = Hero.builder()
                .id(1L)
                .heroClass(HeroClass.WARRIOR)
                .level(5)
                .build();

        this.account = Account.builder()
                .hero(hero)
                .nickname("testuser")
                .password("strongpassword-1`23")
                .build();

        this.itemsInOffer = List.of(
                Item.builder().id(2L).build(),
                Item.builder().id(5L).build(),
                Item.builder().id(5L).build()
        );

        this.itemsResponse = List.of(
                new ItemResponseDto(
                        2L,
                        "Sword of Test",
                        "image1.png",
                        ItemType.WEAPON,
                        HeroClass.WARRIOR,
                        5,
                        0,
                        2,
                        0,
                        3,
                        0,
                        50,
                        3
                ),
                new ItemResponseDto(
                        5L,
                        "Shield of Test",
                        "image2.png",
                        ItemType.ARMOR,
                        HeroClass.WARRIOR,
                        5,
                        0,
                        2,
                        0,
                        3,
                        0,
                        50,
                        3
                ),
                new ItemResponseDto(
                        7L,
                        "Helmet of Test",
                        "image3.png",
                        ItemType.ARMOR,
                        HeroClass.WARRIOR,
                        2,
                        0,
                        1,
                        0,
                        1,
                        0,
                        30,
                        2
                )
        );

        this.shop = Shop.builder()
                .hero(hero)
                .itemsInOffer(itemsInOffer)
                .lastRefresh(LocalDateTime.now())
                .build();

    }

    @Nested
    @DisplayName("get hero shop tests")
    class GetHeroShop {

        @Test
        @DisplayName("Should return hero items")
        void shouldReturnItems () {
//            given
            when(accountRepository.findByEmail(email))
                    .thenReturn(Optional.of(account));

            when(shopRepository.findByHeroId(hero.getId())).thenReturn(shop);

            when(itemMapper.toListResponse(shop.getItemsInOffer())).thenReturn(itemsResponse);

//            when
            ShopResponseDto response = shopService.getHeroShop(email);

//            then
            assertNotNull(response);
            assertEquals(itemsResponse, response.items());
            assertEquals(shop.getLastRefresh(), response.lastRefresh());

            verify(accountRepository).findByEmail(email);
            verify(shopRepository).findByHeroId(hero.getId());
            verify(itemMapper).toListResponse(shop.getItemsInOffer());
            verifyNoMoreInteractions(shopRepository, itemMapper, itemService);

        }
    }
}