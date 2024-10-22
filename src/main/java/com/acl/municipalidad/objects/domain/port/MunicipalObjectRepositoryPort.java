package com.acl.municipalidad.objects.domain.port;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import java.util.List;
import java.util.Optional;

public interface MunicipalObjectRepositoryPort {
    MunicipalObject save(MunicipalObject municipalObject);
    Optional<MunicipalObject> findById(Long id);
    List<MunicipalObject> findByOwnerId(Long ownerId);
    void deleteById(Long id);
}