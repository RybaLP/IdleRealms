package com.ide.realms.IdieRealms.quest.mapper;

import com.ide.realms.IdieRealms.quest.Quest;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestMapper {
    QuestOfferDto questToQuestOfferDto (Quest quest);
    Quest questOfferDtoToQuest (QuestOfferDto questOfferDto);
}
