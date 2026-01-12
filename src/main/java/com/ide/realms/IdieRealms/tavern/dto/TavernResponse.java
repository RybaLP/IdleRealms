package com.ide.realms.IdieRealms.tavern.dto;

import com.ide.realms.IdieRealms.activeQuest.dto.ActiveQuestDto;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;

import java.util.List;

public record TavernResponse(
        List<QuestOfferDto> avalibleQuestOffers,
        ActiveQuestDto activeQuest
){}