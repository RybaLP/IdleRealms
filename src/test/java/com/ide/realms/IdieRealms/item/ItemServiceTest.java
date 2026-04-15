package com.ide.realms.IdieRealms.item;

import com.ide.realms.IdieRealms.exception.ItemsNotFoundException;
import com.ide.realms.IdieRealms.item.mapper.ItemMapper;
import com.ide.realms.IdieRealms.shared.HeroClass;
import com.ide.realms.IdieRealms.shared.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("Item unit tests")
class ItemServiceTest {

    @Mock
    private ItemTemplateRepository itemTemplateRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private ItemTemplate itemTemplate;
    private ItemTemplate itemTemplate2;
    private ItemTemplate itemTemplate3;
    private ItemTemplate itemTemplate4;
    private ItemTemplate itemTemplate5;
    private ItemTemplate itemTemplate6;

    private List<ItemTemplate> listOfItems;

    @BeforeEach
    void setUp () {
        this.itemTemplate = ItemTemplate.builder()
                .heroClass(HeroClass.WARRIOR)
                .type(ItemType.WEAPON)
                .name("WARRIOR-SWORD-1")
                .imageUrl("https://cloud-service.com/myprofile/warriorsword1")
                .build();

        this.itemTemplate2 = ItemTemplate.builder()
                .heroClass(HeroClass.WARRIOR)
                .type(ItemType.GLOVES)
                .name("WARRIOR-GLOVES-1")
                .imageUrl("https://cloud-service.com/myprofile/warriorgloves1")
                .build();

        this.itemTemplate3 = ItemTemplate.builder()
                .heroClass(HeroClass.WARRIOR)
                .type(ItemType.ARMOR)
                .name("WARRIOR-ARMOR-1")
                .imageUrl("https://cloud-service.com/myprofile/warriorarmor1")
                .build();

        this.itemTemplate4 = ItemTemplate.builder()
                .heroClass(HeroClass.WARRIOR)
                .type(ItemType.WEAPON)
                .name("WARRIOR-SWORD-2")
                .imageUrl("https://cloud-service.com/myprofile/warriorsword2")
                .build();

        this.itemTemplate5 = ItemTemplate.builder()
                .heroClass(HeroClass.WARRIOR)
                .type(ItemType.GLOVES)
                .name("WARRIOR-GLOVES-2")
                .imageUrl("https://cloud-service.com/myprofile/warriorgloves2")
                .build();

        this.itemTemplate6 = ItemTemplate.builder()
                .heroClass(HeroClass.WARRIOR)
                .type(ItemType.ARMOR)
                .name("WARRIOR-ARMOR-2")
                .imageUrl("https://cloud-service.com/myprofile/warriorarmor2")
                .build();
    }


    @Nested
    @DisplayName("generateItemEntities()")
    class GenerateItemEntitiesTest {

        @Test
        @DisplayName("Should generate item entities based on templates")
        void shouldGenerateItemEntitiesBasedOnTemplates () {

//            given
            HeroClass heroClass = HeroClass.WARRIOR;
            int heroLvl = 5;

            List<ItemTemplate> items = List.of(
                    itemTemplate,
                    itemTemplate2,
                    itemTemplate3,
                    itemTemplate4,
                    itemTemplate5,
                    itemTemplate6
            );

            when(itemTemplateRepository.find6RandomTemplates(heroClass.name()))
                    .thenReturn(items);

//            when
            List<Item> result = itemService.generateItemEntities(heroLvl,heroClass);

//            then
            assertNotNull(result);
            assertEquals(6, result.size());

        }


        @Test
        @DisplayName("Should assign only strength for warrior as main stat")
        void shouldAssignOnlyStringForWarrior() {
//         given
            HeroClass heroClass = HeroClass.WARRIOR;
            int heroLvl = 10;

            when(itemTemplateRepository.find6RandomTemplates(heroClass.name()))
                    .thenReturn(List.of(itemTemplate));

//            when
            Item item = itemService.generateItemEntities(heroLvl,heroClass).get(0);

//            then
            assertTrue(item.getStrengthBonus() > 0);
            assertEquals(0, item.getDexterityBonus());
            assertEquals(0, item.getIntelligenceBonus());


        }

        @Test
        @DisplayName("Should throw ItemsNotFoundException when no item templates found")
        void shouldThrowItemsNotFoundExceptionWhenTemplatesNotFound() {
            // given
            HeroClass heroClass = HeroClass.WARRIOR;

            when(itemTemplateRepository.find6RandomTemplates(heroClass.name()))
                    .thenReturn(List.of());


//            when and then
            final ItemsNotFoundException exception = assertThrows(
                    ItemsNotFoundException.class,
                    () -> itemService.generateItemEntities(5, heroClass)
            );

            assertEquals("Could not find item template", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Create item from template tests")
    class createItemFromTemplate {

        @Test
        @DisplayName("Should copy basic fields from template to item")
        void shouldCopyFieldsFromTemplateToItem () {

//            given
            HeroClass heroClass = HeroClass.WARRIOR;
            int heroLvl = 7;

            when(itemTemplateRepository.find6RandomTemplates(heroClass.name()))
                    .thenReturn(List.of(itemTemplate));
//            when
            Item item = itemService.generateItemEntities(heroLvl, heroClass).get(0);

//            then
            assertEquals(itemTemplate.getName(), item.getName());
            assertEquals(itemTemplate.getImageUrl(), item.getImageUrl());
            assertEquals(itemTemplate.getType(), item.getItemType());
            assertEquals(itemTemplate.getHeroClass(), item.getHeroClass());
            assertEquals(heroLvl, item.getRequiredLevel());

        }

    }


}