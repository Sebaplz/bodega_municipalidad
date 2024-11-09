package com.acl.municipalidad.items.domain.service;

import com.acl.municipalidad.items.domain.model.Item;

public interface IItemCrudService {
    Item createItem(Item item);
    Item updateItem(Long id, Item item);
    void deleteItem(Long id);
    Item findItemById(Long id);
}
