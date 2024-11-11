package com.acl.municipalidad.images.domain.service;

import com.acl.municipalidad.images.domain.dto.request.ImageRequest;
import com.acl.municipalidad.images.domain.model.Image;

import java.util.List;

public interface IImageCrudService {
    Image createImage(Long itemId, ImageRequest imageRequest);
    Image updateImage(Long id, String url);
    void deleteImage(Long id);
    Image findImageById(Long id);
    List<Image> findByItemId(Long itemId);
}