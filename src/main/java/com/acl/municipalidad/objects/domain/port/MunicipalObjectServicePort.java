package com.acl.municipalidad.objects.domain.port;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MunicipalObjectServicePort {
    MunicipalObject createMunicipalObject(MunicipalObject municipalObject);
    MunicipalObject updateMunicipalObject(Long id, MunicipalObject municipalObject);
    void deleteMunicipalObject(Long id);
    MunicipalObject findMunicipalObjectById(Long id);
    Page<MunicipalObject> findAllByOwner(Long ownerId, Pageable pageable);
    void validateOwnership(Long objectId, User authenticatedUser);
}
