package com.ide.realms.IdieRealms.item;

import com.ide.realms.IdieRealms.hero.dto.HeroProfileResponse;
import com.ide.realms.IdieRealms.item.dto.SwitchItemRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @PutMapping("/switch")
    public ResponseEntity<HeroProfileResponse> switchItem (Principal principal, @Valid @RequestBody SwitchItemRequestDto switchItemRequestDto){
        HeroProfileResponse heroProfileResponse = itemService.switchItem(principal.getName(), switchItemRequestDto.action(), switchItemRequestDto.itemId());
        return ResponseEntity.ok(heroProfileResponse);
    }

}
