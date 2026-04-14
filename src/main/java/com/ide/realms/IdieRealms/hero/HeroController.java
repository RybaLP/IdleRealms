package com.ide.realms.IdieRealms.hero;

import com.ide.realms.IdieRealms.hero.dto.DepositGoldDto;
import com.ide.realms.IdieRealms.hero.dto.HeroProfileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hero")
@Tag(name = "Hero", description = "Endpoints for managing and retrieving hero statistics and profile information")
public class HeroController {

    private final HeroService heroService;

    @GetMapping
    public ResponseEntity<HeroProfileResponse> getHeroInformations(Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(heroService.getHeroInfo(email));
    }

//    guild
    @PostMapping("/guild/deposit")
    public ResponseEntity<Void> depositGoldToGuild ( Authentication authentication,
            @RequestBody @Valid DepositGoldDto depositGoldDto
    ) {
        heroService.depositGoildToGuild(depositGoldDto.amount(), authentication.getName());
        return ResponseEntity.noContent().build();
    }

}
