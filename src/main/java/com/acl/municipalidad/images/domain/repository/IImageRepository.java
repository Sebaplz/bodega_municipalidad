package com.acl.municipalidad.images.domain.repository;

import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.items.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface IImageRepository {
    Image save(Image image, Item item);
    void deleteById(Long id);
    Optional<Image> findById(Long id);
    List<Image> findByItemId(Long itemId);
}