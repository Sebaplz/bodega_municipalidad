package com.acl.municipalidad.images.infraestructure.service;

import com.acl.municipalidad.images.domain.dto.request.ImageRequest;
import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.images.domain.repository.IImageRepository;
import com.acl.municipalidad.images.domain.service.IImageCrudService;
import com.acl.municipalidad.items.domain.repository.IItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageCrudServiceImpl implements IImageCrudService {
    private final IImageRepository imageRepository;
    private final IItemRepository itemRepository;

    private static final int MAX_IMAGES_PER_ITEM = 10;

    @Override
    public Image createImage(Long itemId, ImageRequest imageRequest) {
        return null;
    }

    @Override
    public Image updateImage(Long id, String url) {
        return null;
    }

    @Override
    public void deleteImage(Long id) {

    }

    @Override
    public Image findImageById(Long id) {
        return null;
    }

    @Override
    public List<Image> findByItemId(Long itemId) {
        return null;
    }
}
