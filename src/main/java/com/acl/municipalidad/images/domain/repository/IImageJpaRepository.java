package com.acl.municipalidad.images.domain.repository;

import com.acl.municipalidad.images.domain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImageJpaRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByItemId(Long itemId);
}