package com.ide.realms.IdieRealms.hero;

import com.ide.realms.IdieRealms.hero.dto.HeroProfileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
