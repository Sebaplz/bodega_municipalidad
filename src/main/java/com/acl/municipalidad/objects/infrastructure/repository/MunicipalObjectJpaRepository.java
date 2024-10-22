package com.acl.municipalidad.objects.infrastructure.repository;

import com.acl.municipalidad.objects.infrastructure.entity.MunicipalObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MunicipalObjectJpaRepository extends JpaRepository<MunicipalObjectEntity, Long> {
    List<MunicipalObjectEntity> findByOwnerId(Long ownerId);
}
