package com.acl.municipalidad.items.infrastructure.rest.controller;

import com.acl.municipalidad.items.application.adapter.ItemMapper;
import com.acl.municipalidad.items.domain.dto.request.ItemRequest;
import com.acl.municipalidad.items.domain.dto.response.ApiResponse;
import com.acl.municipalidad.items.domain.dto.response.ItemResponse;
import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.service.IItemCrudService;
import com.acl.municipalidad.items.domain.service.IItemQueryService;
import com.acl.municipalidad.items.domain.service.IItemValidationService;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.infrastructure.service.AuthenticatedUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final IItemCrudService itemCrudService;
    private final IItemQueryService itemQueryService;
    private final IItemValidationService itemValidationService;
    private final AuthenticatedUserService authenticatedUserService;
    private final ItemMapper itemMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid ItemRequest itemRequest) {
        User owner = authenticatedUserService.getAuthenticatedUser();

        // Convertir el DTO de entrada en un objeto Item
        Item item = itemMapper.toDomain(itemRequest);
        item.setOwner(owner);

        // Crear el item
        Item createdItem = itemCrudService.createItem(item);

        // Usar el mapper para convertir el item en un DTO de salida
        ItemResponse responseDto = itemMapper.toResponse(createdItem);

        ApiResponse apiResponse = new ApiResponse("Item created successfully", responseDto);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        User owner = authenticatedUserService.getAuthenticatedUser();
        itemValidationService.validateOwnership(id, owner);

        Item updatedObject = itemCrudService.updateItem(id, itemMapper.toDomain(itemRequest));
        ItemResponse responseDto = itemMapper.toResponse(updatedObject);

        ApiResponse apiResponse = new ApiResponse("Item updated successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User owner = authenticatedUserService.getAuthenticatedUser();
        itemValidationService.validateOwnership(id, owner);
        itemCrudService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        Item item = itemCrudService.findItemById(id);
        ItemResponse responseDto = itemMapper.toResponse(item);
        ApiResponse apiResponse = new ApiResponse("Item found successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/my-items")
    public ResponseEntity<ApiResponse> findAllByOwner(@PageableDefault(size = 10) Pageable pageable) {
        User owner = authenticatedUserService.getAuthenticatedUser();

        // Buscar todos los items que pertenezcan al usuario autenticado con paginación
        Page<Item> itemsPage = itemQueryService.findAllByOwnerId(owner.getId(), pageable);

        // Convertir cada item en un DTO usando el mapper
        List<ItemResponse> responseDtos = itemsPage.stream().map(itemMapper::toResponse).toList();

        // Crear una respuesta con los datos de la página
        Map<String, Object> response = new HashMap<>();
        response.put("items", responseDtos);
        response.put("currentPage", itemsPage.getNumber());
        response.put("totalItems", itemsPage.getTotalElements());
        response.put("totalPages", itemsPage.getTotalPages());

        ApiResponse apiResponse = new ApiResponse("Items found", response);
        return ResponseEntity.ok(apiResponse);
    }
}
