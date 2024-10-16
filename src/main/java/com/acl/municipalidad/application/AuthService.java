package com.acl.municipalidad.application;

import com.acl.municipalidad.domain.response.AuthResponse;
import com.acl.municipalidad.domain.response.AuthenticationRequest;
import com.acl.municipalidad.domain.response.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthenticationRequest request);
}
