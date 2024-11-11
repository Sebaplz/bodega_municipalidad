package com.acl.municipalidad.images.application.mapper;

import com.acl.municipalidad.images.domain.dto.request.ImageRequest;
import com.acl.municipalidad.images.domain.dto.response.ImageResponse;
import com.acl.municipalidad.images.domain.model.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    // Convertir Image a ImageResponse (DTO de salida)
    public ImageResponse toResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getUrl()
        );
    }

    // Convertir ImageRequest (DTO de entrada) a Image (dominio)
    public Image toDomain(ImageRequest imageRequest, Long itemId) {
        return Image.builder()
                .url(imageRequest.getUrl())
                .itemId(itemId)
                .build();
    }
}
