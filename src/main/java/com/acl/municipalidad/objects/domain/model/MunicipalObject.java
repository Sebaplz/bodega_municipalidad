package com.acl.municipalidad.objects.domain.model;

import com.acl.municipalidad.user.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MunicipalObject {
    private Long id;
    private String name;
    private String description;
    private Integer availableQuantity;
    private User owner;
}