package com.acl.municipalidad.items.application.repository;

import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.entity.ItemEntity;
import com.acl.municipalidad.items.domain.repository.IItemJpaRepository;
import com.acl.municipalidad.items.domain.repository.IItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
//TODO: Problema con los nombres?
public class ItemRepositoryAdapter implements IItemRepository {

    private final IItemJpaRepository IItemJpaRepository;

    @Override
    public Item save(Item item) {
        ItemEntity entity = ItemEntity.fromDomain(item);
        ItemEntity savedEntity = IItemJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return IItemJpaRepository.findById(id).map(ItemEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        IItemJpaRepository.deleteById(id);
    }

    @Override
    public Page<Item> findAllByOwnerId(Long ownerId, Pageable pageable) {
        // Obtener una página de ItemsEntity desde el repositorio
        Page<ItemEntity> entityPage = IItemJpaRepository.findByOwnerId(ownerId, pageable);

        // Convertir cada entidad en un item de dominio Items usando map()
        return entityPage.map(this::convertToDomain);
    }

    private Item convertToDomain(ItemEntity entity) {
        Item domainItem = new Item();
        domainItem.setId(entity.getId());
        domainItem.setName(entity.getName());
        domainItem.setDescription(entity.getDescription());
        domainItem.setAvailableQuantity(entity.getAvailableQuantity());
        return domainItem;
    }


}