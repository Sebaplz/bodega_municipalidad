package com.acl.municipalidad.user.domain.port;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.infrastructure.adapter.request.AuthenticationRequest;
import com.acl.municipalidad.user.infrastructure.adapter.request.RegisterRequest;

import java.util.Optional;

public interface UserServicePort {
    Optional<User> findByEmail(String email);
    void registerUser(RegisterRequest request);
    String authenticationUser(AuthenticationRequest request);
}
