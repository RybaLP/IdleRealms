package com.ide.realms.IdieRealms.hero;

import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.hero.dto.HeroProfileResponse;
import com.ide.realms.IdieRealms.infrastructure.adapters.out.kafka.GuildEventPublisher;
import com.ide.realms.IdieRealms.item.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final ItemMapper itemMapper;
    private final AccountRepository accountRepository;
    private final GuildEventPublisher guildEventPublisher;

    public HeroProfileResponse getHeroInfo (String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow( () -> new AccNotExist("Hero with provided id does not exist"));

        Hero hero = account.getHero();

        if (hero == null) {
            throw new AccNotExist("Hero not found");
        }

        hero.refreshBonuses();

        return new HeroProfileResponse(
                hero.getNickname(),
                hero.getHeroClass(),
                hero.getLevel(),
                hero.getExperience(),
                hero.getGold(),
                hero.getEnergy(),
                hero.getVisualConfig(),
                hero.getFinalStatistics(),
                hero.getBaseStats(),
                hero.getSocialId(),
                itemMapper.toListResponse(hero.getInventory()),
                itemMapper.toResponse(hero.getEquippedHelmet()),
                itemMapper.toResponse(hero.getEquippedWeapon()),
                itemMapper.toResponse(hero.getEquippedArmor()),
                itemMapper.toResponse(hero.getEquippedGloves()),
                itemMapper.toResponse(hero.getEquippedBoots())
        );
    }

    @Transactional
    public void processExpAndGold (Long heroId, int goldReward, int expReward){

        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new AccNotExist("Hero with provided ID does not exist"));

        hero.addExperience(expReward);
        hero.setGold(hero.getGold() + goldReward);

        hero.refreshBonuses();
        heroRepository.save(hero);
    }

    @Transactional
    public void consumeEnergy (Long heroId, int amount) {
        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new AccNotExist("Hero with provided ID does not exist"));

        if (hero.getEnergy() < amount) {
            throw new IllegalArgumentException("Not enough energy for this quest");
        }

        hero.setEnergy(hero.getEnergy() - amount);
    }


//     guild
    @Transactional
    public void depositGoildToGuild (long amount, String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist("Account with provided ID does not exist"));

        Hero hero = account.getHero();

        if (hero == null) {
            throw new RuntimeException("Hero does not exist");
        }

        if (hero.getGold() < amount) {
            throw new IllegalArgumentException("Hero does not have enough gold to deposit");
        }

        hero.setGold(hero.getGold() - (int) amount);

        heroRepository.save(hero);

        guildEventPublisher.depositGold(hero.getSocialId(),amount);
    }
}