package com.acl.municipalidad.items.application.adapter;

import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.dto.request.ItemRequest;
import com.acl.municipalidad.items.domain.dto.response.ItemResponse;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    // Convertir Items a ItemsResponse (DTO de salida)
    public ItemResponse toResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailableQuantity()
        );
    }

    // Convertir ItemsRequest (DTO de entrada) a Items (dominio)
    public Item toDomain(ItemRequest itemRequest) {
        return Item.builder()
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .availableQuantity(itemRequest.getAvailableQuantity())
                .build();
    }
}

