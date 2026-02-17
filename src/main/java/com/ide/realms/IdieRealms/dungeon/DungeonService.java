package com.ide.realms.IdieRealms.dungeon;

import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.battle.BattleService;
import com.ide.realms.IdieRealms.battle.dto.PVEbattleResult;
import com.ide.realms.IdieRealms.dungeon.dto.DungeonDetailsDTO;
import com.ide.realms.IdieRealms.dungeon.repository.DungeonFloorRepository;
import com.ide.realms.IdieRealms.dungeon.repository.DungeonRepository;
import com.ide.realms.IdieRealms.dungeon.repository.HeroDungeonProgressRepository;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.monster.Monster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DungeonService {

    private final DungeonRepository dungeonRepository;
    private final DungeonFloorRepository floorRepository;
    private final HeroDungeonProgressRepository progressRepository;
    private final AccountRepository accountRepository;
    private final BattleService battleService;


    public DungeonDetailsDTO getDungeonDetails (String email, Long dungeonId) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist("Could not find account with provided email"));

        Hero hero = account.getHero();
        if (hero == null) {
            throw new RuntimeException("Hero not exists");
        }

        HeroDungeonProgress progress = progressRepository.findByHeroIdAndDungeonId(hero.getId(), dungeonId)
                .orElseGet(() -> {
                    Dungeon dungeon = dungeonRepository.findById(dungeonId)
                            .orElseThrow(() -> new RuntimeException("Provided dungeon does not exist"));

//                    in case if hero did never open provided dungeon before
                    HeroDungeonProgress newProgress = HeroDungeonProgress.builder()
                            .hero(hero)
                            .dungeon(dungeon)
                            .completedFloors(0)
                            .isCompleted(false)
                            .build();

                    return progressRepository.save(newProgress);
                });

        if (progress.isCompleted()) {
            throw new RuntimeException("This dungeon is already completed");
        }

        int currentFloorNumber = progress.getCompletedFloors() + 1;

        DungeonFloor dungeonFloor = floorRepository.findByDungeonIdAndFloorNumber(dungeonId, currentFloorNumber)
                .orElseThrow(() -> new RuntimeException("Could not find dungeon with provided id"));

        Monster monster = dungeonFloor.getMonster();

        return new DungeonDetailsDTO(
                dungeonFloor.getDungeon().getName(),
                currentFloorNumber,
                dungeonFloor.getDescription(),
                monster.getName(),
                monster.getImageUrl(),
                monster.getLevel()
        );
    }


    @Transactional
    public PVEbattleResult pvEbattleResult (String email, Long dungeonid){
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccNotExist("Could not find account with provided email"));

        Hero hero = account.getHero();

        if (hero == null) {
            throw new RuntimeException("Could not find hero");
        }

        HeroDungeonProgress heroDungeonProgress = progressRepository.findByHeroIdAndDungeonId(hero.getId(),dungeonid)
                .orElseThrow(() -> new RuntimeException("No progress found for this dungeon"));

        Monster monster = getCurrentOponent(hero,dungeonid);

        PVEbattleResult pvEbattleResult = battleService.fightAgainstNpc(hero,monster);

        if (pvEbattleResult.isHasPlayerWon()) {
            heroDungeonProgress.setCompletedFloors(heroDungeonProgress.getCompletedFloors() + 1);
            boolean hasNextFloor = floorRepository
                    .findByDungeonIdAndFloorNumber(dungeonid, heroDungeonProgress.getCompletedFloors() + 1)
                    .isPresent();

//            if it was final boss, set dungeon completed to true
            if (!hasNextFloor) {
                heroDungeonProgress.setCompleted(true);
            }

//            rewards
            hero.addExperience(monster.getExpReward());
            hero.setGold(hero.getGold() + monster.getGoldReward());
            progressRepository.save(heroDungeonProgress);
        }

        return pvEbattleResult;
    }

    private Monster getCurrentOponent (Hero hero, Long dungeonid) {

        HeroDungeonProgress progress = progressRepository.findByHeroIdAndDungeonId(hero.getId(), dungeonid)
                .orElseThrow(() -> new RuntimeException("No progress found for this dungeon"));

        int currentFloorNumber = progress.getCompletedFloors() + 1;

        DungeonFloor dungeonFloor = floorRepository.findByDungeonIdAndFloorNumber(dungeonid,currentFloorNumber)
                .orElseThrow(() -> new RuntimeException("No more floors in this dungeon!"));

        return dungeonFloor.getMonster();
    }


}