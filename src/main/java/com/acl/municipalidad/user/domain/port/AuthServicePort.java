package com.acl.municipalidad.user.domain.port;

import com.acl.municipalidad.user.infrastructure.adapter.request.AuthenticationRequest;

public interface AuthServicePort {
    String authenticateUser(AuthenticationRequest request);
}
