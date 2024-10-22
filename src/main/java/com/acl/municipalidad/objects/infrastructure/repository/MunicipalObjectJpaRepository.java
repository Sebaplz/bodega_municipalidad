package com.acl.municipalidad.objects.infrastructure.repository;

import com.acl.municipalidad.objects.infrastructure.entity.MunicipalObjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalObjectJpaRepository extends JpaRepository<MunicipalObjectEntity, Long> {
    Page<MunicipalObjectEntity> findByOwnerId(Long ownerId, Pageable pageable);
}
