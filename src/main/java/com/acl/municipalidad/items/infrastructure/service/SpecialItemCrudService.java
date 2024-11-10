package com.acl.municipalidad.items.infrastructure.service;

import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.repository.IItemRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class SpecialItemCrudService extends ItemCrudServiceImpl {

    public SpecialItemCrudService(IItemRepository itemRepository) {
        super(itemRepository);
    }

    @Override
    public Item createItem(Item item) {
        if (isSpecialItem(item)) {
            item.setName("Special Item: " + item.getName());  // Agrega el título de "Special Item"
        }
        return super.createItem(item);
    }

    private boolean isSpecialItem(Item item) {
        return true;
    }
}

