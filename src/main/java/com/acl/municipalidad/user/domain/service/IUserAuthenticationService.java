package com.acl.municipalidad.user.domain.service;

import com.acl.municipalidad.user.domain.dto.request.AuthenticationRequest;

public interface IUserAuthenticationService {
    String authenticationUser(AuthenticationRequest request);
}