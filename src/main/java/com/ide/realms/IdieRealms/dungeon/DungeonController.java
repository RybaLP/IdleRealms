package com.ide.realms.IdieRealms.dungeon;

import com.ide.realms.IdieRealms.battle.dto.PVEbattleResult;
import com.ide.realms.IdieRealms.dungeon.dto.DungeonDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dungeon")
public class DungeonController {

    private final DungeonService dungeonService;

    @GetMapping("/{dungeonId}/details")
    public ResponseEntity<DungeonDetailsDTO> getDetails (Principal principal, @PathVariable Long dungeonId) {
        DungeonDetailsDTO dungeonDetailsDTO = dungeonService.getDungeonDetails(principal.getName(),dungeonId);
        return ResponseEntity.ok(dungeonDetailsDTO);
    }

    @PostMapping("/{dungeonId}/fight")
    public ResponseEntity<PVEbattleResult> fightAgainstDungeonMonster(Principal principal, @PathVariable Long dungeonId) {
        PVEbattleResult pvEbattleResult = dungeonService.pvEbattleResult(principal.getName(), dungeonId);
        return ResponseEntity.ok(pvEbattleResult);
    }

}
