package com.ide.realms.IdieRealms.item.dto;

import com.ide.realms.IdieRealms.item.Action;

public record SwitchItemRequestDto(
        Action action,
        Long itemId
){}