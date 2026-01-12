package com.ide.realms.IdieRealms.tavern;

import com.ide.realms.IdieRealms.tavern.dto.TavernResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tavern")
@RequiredArgsConstructor
public class TavernController {

    private final TavernService tavernService;

    @GetMapping("/status/{heroId}")
    public ResponseEntity<TavernResponse> getTavernStatus (@PathVariable Long heroId) {
        TavernResponse tavernResponse = tavernService.getQuestOffers(heroId);
        return ResponseEntity.ok(tavernResponse);
    }
}
