package com.ide.realms.IdieRealms.tavern;

import com.ide.realms.IdieRealms.activeQuest.ActiveQuestService;
import com.ide.realms.IdieRealms.activeQuest.dto.ActiveQuestDto;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.quest.QuestService;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import com.ide.realms.IdieRealms.tavern.dto.TavernResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TavernService {

    private final QuestService questService;
    private final ActiveQuestService activeQuestService;
    private final HeroRepository heroRepository;

    public TavernResponse getQuestOffers(Long heroId) {
        ActiveQuestDto activeQuestDto = activeQuestService.getActiveQuestDtoByHeroId(heroId);

        if (activeQuestDto != null) {
            return new TavernResponse(null, activeQuestDto);
        }

        List<QuestOfferDto> questOffers = questService.generate3RandomQuests(heroId);
        return new TavernResponse(questOffers, null);
    }
}