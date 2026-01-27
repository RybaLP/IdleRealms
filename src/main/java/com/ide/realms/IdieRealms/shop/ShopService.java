package com.ide.realms.IdieRealms.shop;

import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.item.ItemService;
import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;
import com.ide.realms.IdieRealms.item.mapper.ItemMapper;
import com.ide.realms.IdieRealms.shop.dto.ShopResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final AccountRepository accountRepository;
    private final ShopRepository shopRepository;
    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @Transactional
    public ShopResponseDto getHeroShop (String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist(""));

        Hero hero = account.getHero();

        if (hero == null) {
            throw new EntityNotFoundException("Could not find hero");
        }

        Shop shop = shopRepository.findByHeroId(hero.getId());

        if (shop == null) {
//            generate shop items and save
            shop = Shop.builder()
                    .hero(hero)
                    .itemsInOffer(itemService.generateItemEntities(hero.getLevel(), hero.getHeroClass()))
                    .lastRefresh(LocalDateTime.now())
                    .build();

            shop = shopRepository.save(shop);
        }

        List<ItemResponseDto> items = itemMapper.toListResponse(shop.getItemsInOffer());

        return new ShopResponseDto(items,shop.getLastRefresh());
    }
}