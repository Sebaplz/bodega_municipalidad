package com.acl.municipalidad.objects.domain.port;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.infrastructure.adapter.request.MunicipalObjectRequest;

import java.util.List;

public interface MunicipalObjectServicePort {
    MunicipalObject createMunicipalObject(MunicipalObject municipalObject);
    MunicipalObject updateMunicipalObject(Long id, MunicipalObject municipalObject);
    void deleteMunicipalObject(Long id);
    MunicipalObject findMunicipalObjectById(Long id);
    List<MunicipalObject> findAllByOwner(Long ownerId);
}
