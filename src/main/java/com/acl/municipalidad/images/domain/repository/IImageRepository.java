package com.acl.municipalidad.images.domain.repository;

import com.acl.municipalidad.images.domain.model.Image;

import java.util.List;

public interface IImageRepository {
    Image save(Image image);
    void deleteById(Long id);
    Image findById(Long id);
    List<Image> findByItemId(Long itemId);
}