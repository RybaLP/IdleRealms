package com.ide.realms.IdieRealms.shop.dto;

import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;

import java.util.List;

public record PurchaseResponseDto(
        List<ItemResponseDto> updatedInventory,
        List<ItemResponseDto> updatedShopItems,
        int currentGold
){}