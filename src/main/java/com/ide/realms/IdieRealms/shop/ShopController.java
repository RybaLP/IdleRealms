package com.ide.realms.IdieRealms.shop;

import com.ide.realms.IdieRealms.shop.dto.PurchaseItemDto;
import com.ide.realms.IdieRealms.shop.dto.PurchaseResponseDto;
import com.ide.realms.IdieRealms.shop.dto.ShopResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/buy")
    public ResponseEntity<PurchaseResponseDto> purchaseItem (Principal principal, @Valid @RequestBody PurchaseItemDto purchaseItemDto) {
        String email = principal.getName();
        PurchaseResponseDto shopResponseDto = shopService.purchaseItem(email,purchaseItemDto.itemId());
        return ResponseEntity.ok(shopResponseDto);
    }

}