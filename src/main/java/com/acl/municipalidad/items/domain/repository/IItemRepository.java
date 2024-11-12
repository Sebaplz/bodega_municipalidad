package com.acl.municipalidad.items.domain.repository;

import com.acl.municipalidad.items.domain.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long id);
    void deleteById(Long id);
    Page<Item> findAllByOwnerId(Long ownerId, Pageable pageable);
}