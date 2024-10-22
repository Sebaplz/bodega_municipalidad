package com.acl.municipalidad.objects.infrastructure.adapter;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectRepositoryPort;
import com.acl.municipalidad.objects.infrastructure.entity.MunicipalObjectEntity;
import com.acl.municipalidad.objects.infrastructure.repository.MunicipalObjectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MunicipalObjectRepositoryAdapter implements MunicipalObjectRepositoryPort {

    private final MunicipalObjectJpaRepository municipalObjectJpaRepository;

    @Override
    public MunicipalObject save(MunicipalObject municipalObject) {
        MunicipalObjectEntity entity = MunicipalObjectEntity.fromDomain(municipalObject);
        MunicipalObjectEntity savedEntity = municipalObjectJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<MunicipalObject> findById(Long id) {
        return municipalObjectJpaRepository.findById(id).map(MunicipalObjectEntity::toDomain);
    }
    @Override
    public void deleteById(Long id) {
        municipalObjectJpaRepository.deleteById(id);
    }

    @Override
    public Page<MunicipalObject> findAllByOwnerId(Long ownerId, Pageable pageable) {
        // Obtener una página de MunicipalObjectEntity desde el repositorio
        Page<MunicipalObjectEntity> entityPage = municipalObjectJpaRepository.findByOwnerId(ownerId, pageable);

        // Convertir cada entidad en un objeto de dominio MunicipalObject usando map()
        return entityPage.map(this::convertToDomain);
    }

    private MunicipalObject convertToDomain(MunicipalObjectEntity entity) {
        MunicipalObject domainObject = new MunicipalObject();
        domainObject.setId(entity.getId());
        domainObject.setName(entity.getName());
        domainObject.setDescription(entity.getDescription());
        domainObject.setAvailableQuantity(entity.getAvailableQuantity());
        return domainObject;
    }


}