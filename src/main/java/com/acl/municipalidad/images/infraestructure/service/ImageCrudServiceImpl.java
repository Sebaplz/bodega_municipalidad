package com.acl.municipalidad.images.infraestructure.service;

import com.acl.municipalidad.images.domain.exceptions.ImageLimitExceededException;
import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.images.domain.repository.IImageRepository;
import com.acl.municipalidad.images.domain.service.IImageCrudService;
import com.acl.municipalidad.items.domain.exceptions.ResourceNotFoundException;
import com.acl.municipalidad.items.domain.model.Item;
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

    // Método privado para encontrar un image por su ID o lanzar una excepción si no se encuentra
    private Image findImageOrThrow(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public Image createImage(Image image, Item item) {
        // Obtener la cantidad de imágenes existentes para el item
        List<Image> existingImages = imageRepository.findByItemId(item.getId());

        // Verificar si se ha alcanzado el límite de imágenes
        if (existingImages.size() >= MAX_IMAGES_PER_ITEM) {
            throw new ImageLimitExceededException("Cannot add more images. Maximum of " + MAX_IMAGES_PER_ITEM + " images per item is allowed.");
        }

        return imageRepository.save(image, item);
    }

    @Override
    public Image updateImage(Long id, String url, Long itemId) {
        // Buscar el Item para confirmar que existe, de lo contrario lanza una excepción
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        // Buscar la imagen en la lista de imágenes asociadas al itemId
        List<Image> existingImages = imageRepository.findByItemId(itemId);
        Image existingImage = existingImages.stream()
                .filter(image -> image.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        // Actualizar la URL de la imagen encontrada
        existingImage.setUrl(url);

        // Guardar la imagen actualizada
        return imageRepository.save(existingImage, item);
    }


    @Override
    public void deleteImage(Long id) {
        Image existingImage = findImageOrThrow(id);
        imageRepository.deleteById(existingImage.getId());
    }

    @Override
    public Image findImageById(Long id) {
        return findImageOrThrow(id);
    }

    @Override
    public List<Image> findByItemId(Long itemId) {
        return imageRepository.findByItemId(itemId);
    }
}
