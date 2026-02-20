package com.ide.realms.IdieRealms.quest;

import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.monster.Monster;
import com.ide.realms.IdieRealms.monster.MonsterRepository;
import com.ide.realms.IdieRealms.monster.TavernMonster;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import com.ide.realms.IdieRealms.quest.mapper.QuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestTemplateRepository questTemplateRepository;
    private final MonsterRepository monsterRepository;
    private final HeroRepository heroRepository;
    private final QuestRepository questRepository;
    private final QuestMapper questMapper;

    @Transactional
    public List<QuestOfferDto> getOrGenerateQuests (Long heroId) {

        List<Quest> existingQuests = questRepository.findByHeroId(heroId);

        if (!existingQuests.isEmpty()) {
            return existingQuests.stream()
                    .map(questMapper::questToQuestOfferDto)
                    .collect(Collectors.toList());
        }

        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new AccNotExist("Hero with provided id does not exist"));

        List<QuestOfferDto> questOfferDtos = List.of(
                generateRandomQuestOffer(hero.getLevel(), 0.7),
                generateRandomQuestOffer(hero.getLevel(), 1.0),
                generateRandomQuestOffer(hero.getLevel(), 1.3)
        );

        List<Quest> quests = questOfferDtos.stream()
                        .map(dto -> {
                            Quest quest = questMapper.questOfferDtoToQuest(dto);
                                    quest.setHero(hero);
                                        return quest;
                        })
                                .collect(Collectors.toList());

        questRepository.saveAll(quests);

        return questOfferDtos;
    }


    private QuestOfferDto generateRandomQuestOffer (int heroLevel, double difficulty) {

        QuestTemplate questTemplate = questTemplateRepository.findRandomQuestTemplate()
                .orElseThrow(() -> new RuntimeException("DB ERROR: Table 'quest_template' is empty!"));

        Monster baseMonster = monsterRepository.findRandomMonster()
                .orElseThrow(() -> new RuntimeException("DB ERROR: Table 'monster' has no 'TAVERN' type monsters!"));

        TavernMonster tavernMonster = new TavernMonster(
                baseMonster,heroLevel,difficulty
        );

        int energyCost;

        if (difficulty < 1.0) {
            energyCost = 5;
        }

        else if (difficulty > 1.0) {
            energyCost = 15;
        } else {
            energyCost = 10;
        }

        int durationInSeconds = energyCost * 30;

        return new QuestOfferDto(
                baseMonster.getId(),
                questTemplate.getTitle(),
                questTemplate.getDescription(),
                questTemplate.getImageUrl(),
                tavernMonster.getFinalStats().goldReward(),
                tavernMonster.getFinalStats().expReward(),
                durationInSeconds,
                difficulty,
                energyCost
        );
    }

    @Transactional
    public void deleteQuestOffersByHeroId (Long heroId) {
        questRepository.deleteByHeroId(heroId);
    }

}