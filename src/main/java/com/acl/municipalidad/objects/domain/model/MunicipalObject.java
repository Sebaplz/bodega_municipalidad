package com.acl.municipalidad.objects.domain.model;

import com.acl.municipalidad.user.domain.model.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalObject {
    private Long id;
    private String name;
    private String description;
    private Integer availableQuantity;
    private User owner;
}