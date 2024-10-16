package com.acl.municipalidad.domain.model;

import com.acl.municipalidad.infrastructure.database.mysql.entity.Role;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private Role role;
}
