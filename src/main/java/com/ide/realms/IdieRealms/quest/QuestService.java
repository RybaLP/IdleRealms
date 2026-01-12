package com.ide.realms.IdieRealms.quest;

import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.monster.Monster;
import com.ide.realms.IdieRealms.monster.MonsterRepository;
import com.ide.realms.IdieRealms.monster.TavernMonster;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestTemplateRepository questTemplateRepository;
    private final MonsterRepository monsterRepository;
    private final HeroRepository heroRepository;

    public List<QuestOfferDto> generate3RandomQuests (Long heroId) {

        int heroLevel = heroRepository.findLevelById(heroId)
                .orElseThrow(() -> new AccNotExist("Could not find hero with provided id"));

        return List.of(
                generateRandomQuestOffer(heroLevel,0.7),
                generateRandomQuestOffer(heroLevel,1.0),
                generateRandomQuestOffer(heroLevel,1.3)
        );
    }

    private QuestOfferDto generateRandomQuestOffer (int heroLevel, double difficulty) {

        QuestTemplate questTemplate = questTemplateRepository.findRandomQuestTemplate()
                .orElseThrow(() -> new RuntimeException(""));

        Monster baseMonster = monsterRepository.findRandomMonster()
                .orElseThrow(() -> new RuntimeException(""));

        TavernMonster tavernMonster = new TavernMonster(
                baseMonster,heroLevel,difficulty
        );

        int durationInSeconds = (int) (120 * difficulty);

        durationInSeconds = Math.min(durationInSeconds, 180);

        return new QuestOfferDto(
                baseMonster.getId(),
                questTemplate.getTitle(),
                questTemplate.getDescription(),
                questTemplate.getImageUrl(),
                tavernMonster.getFinalStats().goldReward(),
                tavernMonster.getFinalStats().expReward(),
                durationInSeconds,
                difficulty
        );
    }
}