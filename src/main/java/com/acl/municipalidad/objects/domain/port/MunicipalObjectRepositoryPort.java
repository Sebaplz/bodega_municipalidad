package com.acl.municipalidad.objects.domain.port;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MunicipalObjectRepositoryPort {
    MunicipalObject save(MunicipalObject municipalObject);
    Optional<MunicipalObject> findById(Long id);
    void deleteById(Long id);
    Page<MunicipalObject> findAllByOwnerId(Long ownerId, Pageable pageable);
}