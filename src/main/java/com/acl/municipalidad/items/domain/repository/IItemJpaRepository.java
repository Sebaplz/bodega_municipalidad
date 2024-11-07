package com.acl.municipalidad.items.domain.repository;

import com.acl.municipalidad.items.domain.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemJpaRepository extends JpaRepository<ItemEntity, Long> {
    Page<ItemEntity> findByOwnerId(Long ownerId, Pageable pageable);
}
