package com.acl.municipalidad.items.infrastructure.service;

import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.repository.IItemRepository;
import com.acl.municipalidad.items.domain.service.IItemCrudService;
import com.acl.municipalidad.items.domain.exceptions.ResourceNotFoundException;
import com.acl.municipalidad.items.domain.exceptions.UnauthorizedException;
import com.acl.municipalidad.items.domain.service.IItemQueryService;
import com.acl.municipalidad.items.domain.service.IItemValidationService;
import com.acl.municipalidad.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCrudServiceImpl implements IItemCrudService, IItemQueryService, IItemValidationService {
    private final IItemRepository itemRepository;

    // Método privado para encontrar un item por su ID o lanzar una excepción si no se encuentra
    private Item findItemOrThrow(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long id, Item item) {
        Item existingItem = findItemOrThrow(id);

        // Actualización parcial: Solo actualiza si el campo no es null
        if (item.getName() != null) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailableQuantity() != null) {
            existingItem.setAvailableQuantity(item.getAvailableQuantity());
        }
        return itemRepository.save(existingItem);
    }

    @Override
    public void deleteItem(Long id) {
        Item existingItem = findItemOrThrow(id);
        itemRepository.deleteById(existingItem.getId());
    }

    @Override
    public Item findItemById(Long id) {
        return findItemOrThrow(id);
    }

    @Override
    public Page<Item> findAllByOwnerId(Long ownerId, Pageable pageable) {
        return itemRepository.findAllByOwnerId(ownerId, pageable);
    }

    public void validateOwnership(Long objectId, User authenticatedUser) {
        Item existingItem = findItemOrThrow(objectId);

        if (!existingItem.getOwner().getId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedException("User does not have permission to modify this object");
        }
    }
}
