package com.acl.municipalidad.images.domain.service;

import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.items.domain.model.Item;

import java.util.List;

public interface IImageCrudService {
    Image createImage(Image image, Item item);
    Image updateImage(Long id, String url, Long itemId);
    void deleteImage(Long id);
    Image findImageById(Long id);
    List<Image> findByItemId(Long itemId);
}