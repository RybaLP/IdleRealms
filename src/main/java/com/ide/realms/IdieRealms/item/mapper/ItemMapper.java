package com.ide.realms.IdieRealms.item.mapper;


import com.ide.realms.IdieRealms.item.Item;
import com.ide.realms.IdieRealms.item.dto.ItemResponseDto;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemResponseDto toResponse (Item item);
    List<ItemResponseDto> toListResponse(List<Item> item);
}