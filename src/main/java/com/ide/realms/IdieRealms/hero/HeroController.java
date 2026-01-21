package com.ide.realms.IdieRealms.hero;

import com.ide.realms.IdieRealms.hero.dto.HeroProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hero")
public class HeroController {

    private final HeroService heroService;

    @GetMapping
    public ResponseEntity<HeroProfileResponse> getHeroInformations(Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(heroService.getHeroInfo(email));
    }

}
