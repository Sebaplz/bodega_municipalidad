package com.acl.municipalidad.items.domain.service;

import com.acl.municipalidad.user.domain.model.User;

public interface IItemValidationService {
    void validateOwnership(Long objectId, User authenticatedUser);
}