package com.acl.municipalidad.images.infraestructure.rest.controller;

import com.acl.municipalidad.images.application.mapper.ImageMapper;
import com.acl.municipalidad.images.domain.dto.request.ImageRequest;
import com.acl.municipalidad.images.domain.dto.response.ImageResponse;
import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.images.domain.service.IImageCrudService;
import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.service.IItemCrudService;
import com.acl.municipalidad.items.domain.service.IItemValidationService;
import com.acl.municipalidad.user.domain.dto.response.ApiResponse;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.infrastructure.service.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final IItemValidationService itemValidationService;
    private final IImageCrudService imageCrudService;
    private final IItemCrudService itemCrudService;
    private final ImageMapper imageMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @PostMapping("/{itemId}")
    public ResponseEntity<ApiResponse> createImage(@PathVariable Long itemId, @RequestBody ImageRequest imageRequest) {
        User owner = authenticatedUserService.getAuthenticatedUser();
        itemValidationService.validateOwnership(itemId, owner);
        Item item = itemCrudService.findItemById(itemId);

        // Convertir el DTO de entrada en un objeto Image
        Image createdImage = imageMapper.toDomain(imageRequest, itemId);

        Image image = imageCrudService.createImage(createdImage, item);

        ImageResponse responseDto = imageMapper.toResponse(image);

        ApiResponse apiResponse = new ApiResponse("Image created successfully", responseDto);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @GetMapping("/all-images/{itemId}")
    public ResponseEntity<ApiResponse> findAllImages(@PathVariable Long itemId) {
        User owner = authenticatedUserService.getAuthenticatedUser();
        itemValidationService.validateOwnership(itemId, owner);

        List<Image> images = imageCrudService.findByItemId(itemId);

        List<ImageResponse> responseDtos = images.stream().map(imageMapper::toResponse).toList();

        ApiResponse apiResponse = new ApiResponse("Images found", responseDtos);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id, @RequestParam Long itemId) {
        User owner = authenticatedUserService.getAuthenticatedUser();
        itemValidationService.validateOwnership(itemId, owner);
        imageCrudService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findImageById(@PathVariable Long id, @RequestParam Long itemId ) {
        User owner = authenticatedUserService.getAuthenticatedUser();
        itemValidationService.validateOwnership(itemId, owner);
        Image image = imageCrudService.findImageById(id);
        ImageResponse responseDto = imageMapper.toResponse(image);
        ApiResponse apiResponse = new ApiResponse("Image found successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long id, @RequestBody ImageRequest imageRequest, @RequestParam Long itemId) {
        User owner = authenticatedUserService.getAuthenticatedUser();
        itemValidationService.validateOwnership(itemId, owner);

        Image updatedObject = imageCrudService.updateImage(id, imageRequest.getUrl(), itemId);
        ImageResponse responseDto = imageMapper.toResponse(updatedObject);

        ApiResponse apiResponse = new ApiResponse("Image updated successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }
}
