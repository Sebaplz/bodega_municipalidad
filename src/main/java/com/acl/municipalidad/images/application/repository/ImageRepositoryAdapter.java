package com.acl.municipalidad.images.application.repository;

import com.acl.municipalidad.images.domain.entity.ImageEntity;
import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.images.domain.repository.IImageJpaRepository;
import com.acl.municipalidad.images.domain.repository.IImageRepository;
import com.acl.municipalidad.items.domain.entity.ItemEntity;
import com.acl.municipalidad.items.domain.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageRepositoryAdapter implements IImageRepository {
    private final IImageJpaRepository IImageJpaRepository;

    @Override
    public Image save(Image image, Item item) {
        ItemEntity itemEntity = ItemEntity.fromDomain(item);
        ImageEntity entity = ImageEntity.fromDomain(image, itemEntity);
        ImageEntity savedEntity = IImageJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public void deleteById(Long id) {
        IImageJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return IImageJpaRepository.findById(id).map(ImageEntity::toDomain);
    }

    @Override
    public List<Image> findByItemId(Long itemId) {
        List<ImageEntity> imageEntities = IImageJpaRepository.findByItemId(itemId);
        return imageEntities.stream().map(ImageEntity::toDomain).toList();
    }
}