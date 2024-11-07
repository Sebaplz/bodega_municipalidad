package com.acl.municipalidad.items.infrastructure.service;

import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.repository.IItemRepository;
import com.acl.municipalidad.items.domain.service.IItemService;
import com.acl.municipalidad.items.infrastructure.exceptions.ResourceNotFoundException;
import com.acl.municipalidad.items.infrastructure.exceptions.UnauthorizedException;
import com.acl.municipalidad.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {
    private final IItemRepository itemRepository;

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long id, Item item) {
        Item existingObject = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        existingObject.setName(item.getName());
        existingObject.setDescription(item.getDescription());
        existingObject.setAvailableQuantity(item.getAvailableQuantity());

        return itemRepository.save(existingObject);
    }

    @Override
    public void deleteItem(Long id) {
        Item existingObject = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        itemRepository.deleteById(id);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

    @Override
    public Page<Item> findAllByOwner(Long ownerId, Pageable pageable) {
        return itemRepository.findAllByOwnerId(ownerId, pageable);
    }

    public void validateOwnership(Long objectId, User authenticatedUser) {
        Item existingObject = itemRepository.findById(objectId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if (!existingObject.getOwner().getId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedException("User does not have permission to modify this object");
        }
    }

}
