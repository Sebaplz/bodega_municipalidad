package com.acl.municipalidad.user.domain.service;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.dto.request.AuthenticationRequest;
import com.acl.municipalidad.user.domain.dto.request.RegisterRequest;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);
    void registerUser(RegisterRequest request);
    String authenticationUser(AuthenticationRequest request);
}
