package com.acl.municipalidad.images.application.mapper;

import com.acl.municipalidad.images.domain.entity.ImageEntity;
import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.items.domain.entity.ItemEntity;

public class ImageMapper {
    public static Image toDomainModel(ImageEntity entity) {
        return Image.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .itemId(entity.getItem().getId())
                .build();
    }

    public static ImageEntity toEntity(Image image, ItemEntity itemEntity) {
        return ImageEntity.builder()
                .url(image.getUrl())
                .item(itemEntity)
                .build();
    }
}
