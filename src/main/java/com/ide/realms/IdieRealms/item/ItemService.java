package com.ide.realms.IdieRealms.item;

import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.hero.dto.BaseStatsDto;
import com.ide.realms.IdieRealms.hero.dto.HeroFinalStatsDto;
import com.ide.realms.IdieRealms.hero.dto.HeroProfileResponse;
import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;
import com.ide.realms.IdieRealms.item.mapper.ItemMapper;
import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemTemplateRepository itemTemplateRepository;
    private final ItemMapper itemMapper;
    private final AccountRepository accountRepository;
    private final HeroRepository heroRepository;

    public List<Item> generateItemEntities (int heroLvl, HeroClass heroClass) {
        List<ItemTemplate> itemTemplates = itemTemplateRepository.find6RandomTemplates(heroClass.name());

        return itemTemplates.stream()
                .map(template -> createItemFromTemplate(template,heroLvl))
                .toList();
    }

    public List<ItemResponseDto> generateItem(int heroLvl, HeroClass heroClass) {
        List<Item> entities = generateItemEntities(heroLvl, heroClass);
        return itemMapper.toListResponse(entities);
    }

    public Item generateItemEntity (int heroLvl, HeroClass heroClass) {
        ItemTemplate itemTemplate = itemTemplateRepository.find6RandomTemplates(heroClass.name()).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find item template"));

        Item item = createItemFromTemplate(itemTemplate,heroLvl);

        return item;
    }

    private Item createItemFromTemplate(ItemTemplate template, int heroLvl) {
        int pointsToDistribute = heroLvl * 2 + 2;
        int power = heroLvl * 3 + (int)(Math.random() * 5);

        Item item = Item.builder()
                .name(template.getName())
                .imageUrl(template.getImageUrl())
                .itemType(template.getType())
                .heroClass(template.getHeroClass())
                .requiredLevel(heroLvl)
                .power(power)
                .price(power * 10)
                .build();

        assignRandomStats(item, pointsToDistribute, template.getHeroClass());

        return item;
    }

    private void assignRandomStats(Item item, int points, HeroClass heroClass) {
        int mainStat = (int) (points * 0.7);
        int leftovers = points - mainStat;

        switch (heroClass) {
            case WARRIOR -> item.setStrengthBonus(mainStat);
            case SCOUT -> item.setDexterityBonus(mainStat);
            case MAGE -> item.setIntelligenceBonus(mainStat);
        }

        if (Math.random() > 0.5) {
            item.setConstitutionBonus(leftovers);
        } else {
            item.setLuckBonus(leftovers);
        }
    }

    @Transactional
    public HeroProfileResponse switchItem(String email, Action action, Long itemId) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist("Account with provided email does not exist"));

        Hero hero = account.getHero();
        if (hero == null) throw new RuntimeException("Could not find hero");

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Could not find item with provided id"));

        if (action.equals(Action.EQUIP)) {
            equipItem(hero, item);
        } else if (action.equals(Action.TAKE_OFF)) {
            takeOffItem(hero, item);
        }

        hero.refreshBonuses();
        heroRepository.save(hero);

        return new HeroProfileResponse(
                hero.getNickname(),
                hero.getHeroClass(),
                hero.getLevel(),
                hero.getExperience(),
                hero.getGold(),
                hero.getEnergy(),
                hero.getVisualConfig(),

                new HeroFinalStatsDto(
                        hero.getStrength(),
                        hero.getDexterity(),
                        hero.getIntelligence(),
                        hero.getConstitution(),
                        hero.getLuck(),
                        hero.getTotalArmor(),
                        hero.calculateFullHp(),
                        hero.getLevel()
                ),

                new BaseStatsDto(
                        hero.getBaseStats().strength(),
                        hero.getBaseStats().dexterity(),
                        hero.getBaseStats().intelligence(),
                        hero.getBaseStats().constitution(),
                        hero.getBaseStats().luck()
                ),

                itemMapper.toListResponse(hero.getInventory()),
                itemMapper.toResponse(hero.getEquippedHelmet()),
                itemMapper.toResponse(hero.getEquippedWeapon()),
                itemMapper.toResponse(hero.getEquippedArmor()),
                itemMapper.toResponse(hero.getEquippedGloves()),
                itemMapper.toResponse(hero.getEquippedBoots())
        );
    }

    @Transactional
    private void equipItem(Hero hero, Item newItem) {
        if (newItem.getHeroClass() != hero.getHeroClass()) {
            throw new RuntimeException("This item is not for this class");
        }

        Item oldItem = switch (newItem.getItemType()) {
            case WEAPON -> hero.getEquippedWeapon();
            case HELMET -> hero.getEquippedHelmet();
            case ARMOR -> hero.getEquippedArmor();
            case GLOVES -> hero.getEquippedGloves();
            case BOOTS -> hero.getEquippedBoots();
            default -> throw new RuntimeException("Unsupported item type");
        };

        if (oldItem != null) {
            hero.getInventory().add(oldItem);
        }

        switch (newItem.getItemType()) {
            case WEAPON -> hero.setEquippedWeapon(newItem);
            case HELMET -> hero.setEquippedHelmet(newItem);
            case ARMOR -> hero.setEquippedArmor(newItem);
            case GLOVES -> hero.setEquippedGloves(newItem);
            case BOOTS -> hero.setEquippedBoots(newItem);
        }

        hero.getInventory().remove(newItem);
    }

    @Transactional
    private void takeOffItem (Hero hero , Item item) {

//        in case : inventory full
        if (hero.getInventory().size() >= 5) {
            throw new RuntimeException("Inventory is full already");
        }

        switch (item.getItemType()) {
            case ARMOR -> hero.setEquippedArmor(null);
            case WEAPON -> hero.setEquippedWeapon(null);
            case BOOTS -> hero.setEquippedBoots(null);
            case HELMET -> hero.setEquippedHelmet(null);
            case GLOVES -> hero.setEquippedGloves(null);
            default -> throw new RuntimeException("Unknown item type");
        }

        hero.getInventory().add(item);

    }
}
