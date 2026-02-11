package com.ide.realms.IdieRealms.item;

import com.ide.realms.IdieRealms.exception.ItemsNotFoundException;
import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;
import com.ide.realms.IdieRealms.item.mapper.ItemMapper;
import com.ide.realms.IdieRealms.shared.HeroClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemTemplateRepository itemTemplateRepository;
    private final ItemMapper itemMapper;

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
}
