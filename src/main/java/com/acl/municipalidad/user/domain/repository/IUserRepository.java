package com.acl.municipalidad.user.domain.repository;

import com.acl.municipalidad.user.domain.model.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    void save(User user);
}