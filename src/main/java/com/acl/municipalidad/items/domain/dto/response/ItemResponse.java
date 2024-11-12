package com.acl.municipalidad.items.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Integer availableQuantity;
}