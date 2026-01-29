package com.ide.realms.IdieRealms.tavern;

import com.ide.realms.IdieRealms.activeQuest.ActiveQuest;
import com.ide.realms.IdieRealms.activeQuest.ActiveQuestRepository;
import com.ide.realms.IdieRealms.activeQuest.ActiveQuestService;
import com.ide.realms.IdieRealms.activeQuest.dto.ActiveQuestDto;
import com.ide.realms.IdieRealms.activeQuest.mapper.ActiveQuestMapper;
import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.exception.MonsterNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.monster.Monster;
import com.ide.realms.IdieRealms.monster.MonsterRepository;
import com.ide.realms.IdieRealms.quest.QuestService;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import com.ide.realms.IdieRealms.tavern.dto.TavernResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TavernService {

    private final QuestService questService;
    private final ActiveQuestService activeQuestService;
    private final HeroRepository heroRepository;
    private final ActiveQuestRepository activeQuestRepository;
    private final ActiveQuestMapper activeQuestMapper;
    private final MonsterRepository monsterRepository;
    private final AccountRepository accountRepository;

    public TavernResponse getQuestOffers(String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist(""));

        ActiveQuestDto activeQuestDto = activeQuestService.getActiveQuestDtoByHeroId(account.getHero().getId());

        if (activeQuestDto != null) {
            return new TavernResponse(null, activeQuestDto);
        }

        List<QuestOfferDto> questOffers = questService.getOrGenerateQuests(account.getHero().getId());
        return new TavernResponse(questOffers, null);
    }

    @Transactional
    public ActiveQuestDto acceptQuestOffer (QuestOfferDto questOfferDto, String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist(""));

        Hero hero = heroRepository.findById(account.getHero().getId())
                .orElseThrow(() -> new AccNotExist("Hero with provided id does not exist"));

        if (activeQuestRepository.existsByHero(hero)) {
            throw new IllegalArgumentException("Hero is already in different mission");
        }

        if (hero.getEnergy() < questOfferDto.energyCost()) {
            throw new IllegalArgumentException("Not enough energy");
        }

        hero.setEnergy(hero.getEnergy() - questOfferDto.energyCost());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finishTime = now.plusSeconds(questOfferDto.durationInSeconds());

        ActiveQuest activeQuest = ActiveQuest.builder()
                .goldReward(questOfferDto.goldReward())
                .expReward(questOfferDto.expReward())
                .startTime(now)
                .finishTime(finishTime)
                .completed(false)
                .monsterId(questOfferDto.monsterId())
                .difficultyMultiplier(questOfferDto.difficulty())
                .hero(hero)
                .rewardsClaimed(false)
                .title(questOfferDto.title())
                .imageUrl(questOfferDto.imageUrl())
                .build();

        activeQuestRepository.save(activeQuest);

        //        clean up
        questService.deleteQuestOffersByHeroId(account.getHero().getId());

        Monster monster = monsterRepository.findById(questOfferDto.monsterId())
                .orElseThrow(() -> new MonsterNotExist("Monster with provided id does not exist"));
        return activeQuestMapper.isActiveQuestDto(activeQuest,monster);
    }
}