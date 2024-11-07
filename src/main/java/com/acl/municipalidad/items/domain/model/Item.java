package com.acl.municipalidad.items.domain.model;

import com.acl.municipalidad.user.domain.model.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private String description;
    private Integer availableQuantity;
    private User owner;
}