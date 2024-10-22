package com.acl.municipalidad.user.domain.port;

import com.acl.municipalidad.user.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    void save(User user);
}
