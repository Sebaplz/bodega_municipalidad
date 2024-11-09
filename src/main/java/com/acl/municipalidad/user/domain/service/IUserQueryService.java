package com.acl.municipalidad.user.domain.service;

import com.acl.municipalidad.user.domain.model.User;

import java.util.Optional;

public interface IUserQueryService {
    Optional<User> findByEmail(String email);
}