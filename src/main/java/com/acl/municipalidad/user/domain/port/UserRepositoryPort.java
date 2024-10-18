package com.acl.municipalidad.user.domain.port;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.infrastructure.adapter.request.RegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    void save(RegisterRequest request);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
