package com.ide.realms.IdieRealms.activeQuest;

import com.ide.realms.IdieRealms.activeQuest.dto.ActiveQuestDto;
import com.ide.realms.IdieRealms.activeQuest.mapper.ActiveQuestMapper;
import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.battle.BattleService;
import com.ide.realms.IdieRealms.battle.dto.PVEbattleResult;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.exception.MonsterNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.hero.HeroService;
import com.ide.realms.IdieRealms.monster.Monster;
import com.ide.realms.IdieRealms.monster.MonsterRepository;
import com.ide.realms.IdieRealms.monster.TavernMonster;
import com.ide.realms.IdieRealms.quest.Quest;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActiveQuestService {

    private final ActiveQuestRepository activeQuestRepository;
    private final HeroRepository heroRepository;
    private final ActiveQuestMapper activeQuestMapper;
    private final MonsterRepository monsterRepository;
    private final AccountRepository accountRepository;
    private final BattleService battleService;
    private final HeroService heroService;


    @Transactional
    public ActiveQuestDto createActiveQuestDto (QuestOfferDto questOfferDto, Long heroId){

        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new AccNotExist("Hero with provided id does not exist"));

        if (activeQuestRepository.existsByHero(hero)) {
            throw new IllegalMonitorStateException("Hero is already in mission");
        }

        LocalDateTime localDateTime = LocalDateTime.now(java.time.ZoneOffset.UTC);

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


    @Transactional
    public PVEbattleResult resultOfMission(String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist("Account with provided id does not exist"));

        Hero hero = account.getHero();

        ActiveQuest activeQuest = activeQuestRepository.findByHeroId(account.getHero().getId())
                .orElseThrow(() -> new IllegalArgumentException(""));

//        checking if time passed
        LocalDateTime now = LocalDateTime.now(java.time.ZoneOffset.UTC);

        if (now.isBefore(activeQuest.getFinishTime())) {
            throw new IllegalStateException("Mission is still in progress. Time left: " +
                    Duration.between(now, activeQuest.getFinishTime()).getSeconds() + "s");
        }

        Monster baseMonster = monsterRepository.findById(activeQuest.getMonsterId())
                .orElseThrow(() -> new MonsterNotExist("Monster with provided id does not exist"));

        TavernMonster tavernMonster = new TavernMonster(
                baseMonster,
                hero.getLevel(),
                activeQuest.getDifficultyMultiplier(),
                activeQuest.getGoldReward(),
                activeQuest.getExpReward()
        );

        PVEbattleResult pvEbattleResult = battleService.fightAgainstNpc(hero,tavernMonster);

//        check if hero won fight
        if (pvEbattleResult.isHasPlayerWon()) {
            heroService.processExpAndGold(hero.getId(), pvEbattleResult.getEarnedGold(), pvEbattleResult.getEarnedExp());
        }

        activeQuestRepository.delete(activeQuest);

        heroRepository.save(hero);

        return pvEbattleResult;
    }
}