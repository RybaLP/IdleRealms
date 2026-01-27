package com.ide.realms.IdieRealms.shop;

import com.ide.realms.IdieRealms.shop.dto.ShopResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<ShopResponseDto> getOrGenerateShopItems (Principal principal) {
        String email = principal.getName();
        ShopResponseDto shopResponseDto = shopService.getHeroShop(email);
        return ResponseEntity.ok(shopResponseDto);
    }

}
