package com.ide.realms.IdieRealms.shop.dto;

import jakarta.validation.constraints.NotNull;

public record PurchaseItemDto(
        @NotNull Long itemId
){}