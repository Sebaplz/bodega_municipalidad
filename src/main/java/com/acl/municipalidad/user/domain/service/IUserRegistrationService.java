package com.acl.municipalidad.user.domain.service;

import com.acl.municipalidad.user.domain.dto.request.RegisterRequest;

public interface IUserRegistrationService {
    void registerUser(RegisterRequest request);
}
