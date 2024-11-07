package com.acl.municipalidad.items.domain.service;

import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemService {
    Item createItem(Item item);
    Item updateItem(Long id, Item item);
    void deleteItem(Long id);
    Item findItemById(Long id);
    Page<Item> findAllByOwnerId(Long ownerId, Pageable pageable);
    void validateOwnership(Long objectId, User authenticatedUser);
}
