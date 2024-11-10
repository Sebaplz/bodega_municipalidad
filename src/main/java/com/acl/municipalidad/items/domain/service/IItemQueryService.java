package com.acl.municipalidad.items.domain.service;

import com.acl.municipalidad.items.domain.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemQueryService {
    Page<Item> findAllByOwnerId(Long ownerId, Pageable pageable);
}