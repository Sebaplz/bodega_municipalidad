package com.acl.municipalidad.objects.infrastructure.adapter.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MunicipalObjectResponse {
    private Long id;
    private String name;
    private String description;
    private Integer availableQuantity;
}