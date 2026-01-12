package com.ide.realms.IdieRealms.activeQuest;

import com.ide.realms.IdieRealms.activeQuest.dto.ActiveQuestDto;
import com.ide.realms.IdieRealms.activeQuest.mapper.ActiveQuestMapper;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.exception.MonsterNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.monster.Monster;
import com.ide.realms.IdieRealms.monster.MonsterRepository;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActiveQuestService {

    private final ActiveQuestRepository activeQuestRepository;
    private final HeroRepository heroRepository;
    private final ActiveQuestMapper activeQuestMapper;
    private final MonsterRepository monsterRepository;

    @Transactional
    public ActiveQuestDto createActiveQuestDto (QuestOfferDto questOfferDto, Long heroId){

        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new AccNotExist("Hero with provided id does not exist"));

        if (activeQuestRepository.existsByHero(hero)) {
            throw new IllegalMonitorStateException("Hero is already in mission");
        }

        LocalDateTime localDateTime = LocalDateTime.now();

        ActiveQuest activeQuest = ActiveQuest.builder()
                .hero(hero)
                .monsterId(questOfferDto.monsterId())
                .difficultyMultiplier(questOfferDto.difficulty())
                .goldReward(questOfferDto.goldReward())
                .expReward(questOfferDto.expReward())
                .startTime(localDateTime)
                .finishTime(localDateTime.plusSeconds(questOfferDto.durationInSeconds()))
                .completed(false)
                .rewardsClaimed(false)
                .build();

        ActiveQuest savedQuest = activeQuestRepository.save(activeQuest);

        Monster monster = monsterRepository.findById(questOfferDto.monsterId())
                .orElseThrow(() -> new RuntimeException("Monster with provided id does not exist"));

        return activeQuestMapper.isActiveQuestDto(savedQuest,monster);
    }

    public ActiveQuestDto getActiveQuestDtoByHeroId (Long heroId) {

        return activeQuestRepository.findByHeroId(heroId)
                .map(activeQuest -> {
                    Monster monster = monsterRepository.findById(activeQuest.getMonsterId())
                            .orElseThrow(() -> new MonsterNotExist("Monster with provided id does not exist"));

                    return activeQuestMapper.isActiveQuestDto(activeQuest,monster);
                })
                .orElse(null);
    }
}