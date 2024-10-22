package com.acl.municipalidad.objects.infrastructure.adapter;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectRepositoryPort;
import com.acl.municipalidad.objects.infrastructure.entity.MunicipalObjectEntity;
import com.acl.municipalidad.objects.infrastructure.repository.MunicipalObjectJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<MunicipalObject> findByOwnerId(Long ownerId) {
        return municipalObjectJpaRepository.findByOwnerId(ownerId).stream()
                .map(MunicipalObjectEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        municipalObjectJpaRepository.deleteById(id);
    }
}