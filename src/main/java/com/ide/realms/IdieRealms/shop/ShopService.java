package com.ide.realms.IdieRealms.shop;

import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.item.Item;
import com.ide.realms.IdieRealms.item.ItemService;
import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;
import com.ide.realms.IdieRealms.item.mapper.ItemMapper;
import com.ide.realms.IdieRealms.shop.dto.PurchaseResponseDto;
import com.ide.realms.IdieRealms.shop.dto.ShopResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final AccountRepository accountRepository;
    private final ShopRepository shopRepository;
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final HeroRepository heroRepository;

    @Transactional
    public ShopResponseDto getHeroShop (String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist("Account with provided email does not exist"));

        Hero hero = account.getHero();

        if (hero == null) {
            throw new EntityNotFoundException("Could not find hero");
        }

        Shop shop = shopRepository.findByHeroId(hero.getId());

        if (shop == null){
//            generate shop items and save
            shop = Shop.builder().hero(hero).build();
            shop = shopRepository.save(shop);
            refreshShopItems(shop,hero);
            shop = shopRepository.save(shop);

        } else if (shouldRefresh(shop) || shop.getItemsInOffer().isEmpty()) {
            refreshShopItems(shop,hero);
            shop = shopRepository.save(shop);
        }

        List<ItemResponseDto> items = itemMapper.toListResponse(shop.getItemsInOffer());
        return new ShopResponseDto(items,shop.getLastRefresh());
    }


    @Transactional
    public PurchaseResponseDto purchaseItem (String email, Long id) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist("Account with provided email does not exist"));

        Hero hero = account.getHero();

        if (hero == null) {
            throw new AccNotExist("Could not find hero");
        }

        int heroGold = hero.getGold();

        Shop shop = shopRepository.findByHeroId(hero.getId());

        if (shop == null) {
            throw new IllegalStateException("Shop not initialized");
        }

        List<Item> offer = shop.getItemsInOffer();

        int itemIndex = -1;

        for (int i = 0; i < offer.size(); i++) {
            if (offer.get(i).getId().equals(id)) {
                itemIndex = i;
                break;
            }
        }

        if (itemIndex == -1) {
            throw new IllegalStateException("Item not found in shop");
        }

        Item item = offer.get(itemIndex);

        if (item == null) {
            throw new IllegalStateException("Could not find item");
        }

        if (item.getPrice() > heroGold) {
            throw new IllegalStateException("Hero does not have enough gold to purchase that item");
        }

        if (hero.getInventory().size() >= 5) {
            throw new IllegalStateException("Inventory is full");
        }

        Item purchasedItem = Item.builder()
                .name(item.getName())
                .power(item.getPower())
                .price(item.getPrice())
                .itemType(item.getItemType())
                .imageUrl(item.getImageUrl())
                .strengthBonus(item.getStrengthBonus())
                .dexterityBonus(item.getDexterityBonus())
                .intelligenceBonus(item.getIntelligenceBonus())
                .constitutionBonus(item.getConstitutionBonus())
                .luckBonus(item.getLuckBonus())
                .heroClass(item.getHeroClass())
                .requiredLevel(item.getRequiredLevel())
                .shop(null)
                .build();

        hero.setGold(heroGold - item.getPrice());
        hero.getInventory().add(purchasedItem);

        Item newItem = itemService.generateItemEntity(hero.getLevel(), hero.getHeroClass());
        newItem.setShop(shop);

        offer.remove(itemIndex);
        offer.add(itemIndex, newItem);

        heroRepository.save(hero);
        shopRepository.save(shop);

        return new PurchaseResponseDto(
                itemMapper.toListResponse(hero.getInventory()),
                itemMapper.toListResponse(shop.getItemsInOffer()),
                hero.getGold()
        );
    }

    @Transactional
    private void refreshShopItems(Shop shop, Hero hero) {
        List<Item> newItems = itemService.generateItemEntities(hero.getLevel(), hero.getHeroClass());
        shop.getItemsInOffer().clear();

        for (Item item : newItems) {
            item.setShop(shop);
        }

        shop.getItemsInOffer().addAll(newItems);
        shop.setLastRefresh(LocalDateTime.now());
    }

    private boolean shouldRefresh(Shop shop) {
        if (shop.getLastRefresh() == null) return true;

        LocalDateTime lastRefresh = shop.getLastRefresh();
        LocalDateTime lastMidnight = LocalDate.now().atStartOfDay();
        return lastRefresh.isBefore(lastMidnight);
    }


}