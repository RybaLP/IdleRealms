package com.ide.realms.IdieRealms.battle;

import com.ide.realms.IdieRealms.auth.Account;
import com.ide.realms.IdieRealms.auth.AccountRepository;
import com.ide.realms.IdieRealms.battle.dto.PVEbattleResult;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.exception.MonsterNotExist;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.hero.HeroRepository;
import com.ide.realms.IdieRealms.monster.Monster;
import com.ide.realms.IdieRealms.monster.MonsterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;
    private final MonsterRepository monsterRepository;
    private final AccountRepository accountRepository;

    @PostMapping("/api/battle/{monsterId}")
    public ResponseEntity<PVEbattleResult> fightMonster (@PathVariable Long monsterId, Principal principal){
        String accountEmail = principal.getName();

        Account account = accountRepository.findByEmail(accountEmail)
                .orElseThrow(() -> new AccNotExist("Account with provided email does not exist"));

        Hero hero = account.getHero();
        Monster monster = monsterRepository.findById(monsterId)
                .orElseThrow(() -> new MonsterNotExist("Monster with provided id does not exist"));

        PVEbattleResult pvEbattleResult = battleService.fightAgainstNpc(hero, monster);

        return ResponseEntity.ok(pvEbattleResult);
    }

}
