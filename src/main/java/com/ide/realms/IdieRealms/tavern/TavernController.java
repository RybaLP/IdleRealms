package com.ide.realms.IdieRealms.tavern;

import com.ide.realms.IdieRealms.activeQuest.ActiveQuestService;
import com.ide.realms.IdieRealms.activeQuest.dto.ActiveQuestDto;
import com.ide.realms.IdieRealms.battle.dto.PVEbattleResult;
import com.ide.realms.IdieRealms.quest.dto.QuestOfferDto;
import com.ide.realms.IdieRealms.tavern.dto.TavernResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/tavern")
@RequiredArgsConstructor
@Tag(name = "Tavern", description = "Everything related to quests and tavern monsters")
public class TavernController {

    private final TavernService tavernService;
    private final ActiveQuestService activeQuestService;

    @GetMapping("/status")
    public ResponseEntity<TavernResponse> getTavernStatus (Principal principal) {
        TavernResponse tavernResponse = tavernService.getQuestOffers(principal.getName());
        return ResponseEntity.ok(tavernResponse);
    }

    @PostMapping("/accept")
    public ResponseEntity<ActiveQuestDto> acceptQuest (@RequestBody QuestOfferDto questOfferDto, Principal principal) {
        ActiveQuestDto activeQuestDto = tavernService.acceptQuestOffer(questOfferDto, principal.getName());
        return ResponseEntity.ok(activeQuestDto);
    }

    @PostMapping("/claim")
    public ResponseEntity<PVEbattleResult> claimReward (Principal principal) {
        PVEbattleResult result = activeQuestService.resultOfMission(principal.getName());
        return ResponseEntity.ok(result);
    }

}