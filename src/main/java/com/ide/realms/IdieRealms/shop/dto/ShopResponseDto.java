package com.ide.realms.IdieRealms.shop.dto;

import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record ShopResponseDto(
        List<ItemResponseDto> items,
        LocalDateTime lastRefresh
){}
