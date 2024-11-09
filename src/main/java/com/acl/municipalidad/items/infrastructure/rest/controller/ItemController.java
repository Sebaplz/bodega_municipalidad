package com.acl.municipalidad.items.infrastructure.rest.controller;

import com.acl.municipalidad.items.application.adapter.ItemMapper;
import com.acl.municipalidad.items.domain.dto.request.ItemRequest;
import com.acl.municipalidad.items.domain.dto.response.ApiResponse;
import com.acl.municipalidad.items.domain.dto.response.ItemResponse;
import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.items.domain.service.IItemService;
import com.acl.municipalidad.items.infrastructure.service.SpecialItemService;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService IItemService;
    private final SpecialItemService specialItemService;
    private final IUserService IUserService;
    private final ItemMapper itemMapper;

    // Método privado para obtener el usuario autenticado
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new RuntimeException("User is not authenticated");
        }

        String email = userDetails.getUsername();

        return IUserService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid ItemRequest itemRequest) {
        User owner = getAuthenticatedUser();

        // Convertir el DTO de entrada en un objeto Item
        Item item = itemMapper.toDomain(itemRequest);
        item.setOwner(owner);

        // Crear el item
        Item createdItem = specialItemService.createItem(item);

        // Usar el mapper para convertir el item en un DTO de salida
        ItemResponse responseDto = itemMapper.toResponse(createdItem);

        ApiResponse apiResponse = new ApiResponse("Item created successfully", responseDto);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        User authenticatedUser = getAuthenticatedUser();
        IItemService.validateOwnership(id, authenticatedUser);

        Item updatedObject = IItemService.updateItem(id, itemMapper.toDomain(itemRequest));
        ItemResponse responseDto = itemMapper.toResponse(updatedObject);

        ApiResponse apiResponse = new ApiResponse("Item updated successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User authenticatedUser = getAuthenticatedUser();
        IItemService.validateOwnership(id, authenticatedUser);
        IItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        Item item = IItemService.findItemById(id);
        ItemResponse responseDto = itemMapper.toResponse(item);
        ApiResponse apiResponse = new ApiResponse("Item found successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/my-items")
    public ResponseEntity<ApiResponse> findAllByOwner(@PageableDefault(size = 10) Pageable pageable) {
        User owner = getAuthenticatedUser();

        // Buscar todos los items que pertenezcan al usuario autenticado con paginación
        Page<Item> itemsPage = IItemService.findAllByOwnerId(owner.getId(), pageable);

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
