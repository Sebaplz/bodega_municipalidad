package com.acl.municipalidad.objects.infrastructure.rest.controller;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectServicePort;
import com.acl.municipalidad.objects.infrastructure.adapter.mapper.MunicipalObjectMapper;
import com.acl.municipalidad.objects.infrastructure.adapter.request.MunicipalObjectRequest;
import com.acl.municipalidad.objects.infrastructure.adapter.response.ApiResponse;
import com.acl.municipalidad.objects.infrastructure.adapter.response.MunicipalObjectResponse;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.port.UserServicePort;
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
@RequestMapping("/api/v1/municipal-objects")
@RequiredArgsConstructor
public class MunicipalObjectController {

    private final MunicipalObjectServicePort municipalObjectServicePort;
    private final UserServicePort userServicePort;
    private final MunicipalObjectMapper municipalObjectMapper;

    // Método privado para obtener el usuario autenticado
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new RuntimeException("User is not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        return userServicePort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid MunicipalObjectRequest municipalObjectRequest) {
        User owner = getAuthenticatedUser();

        // Convertir el DTO de entrada en un objeto MunicipalObject
        MunicipalObject municipalObject = municipalObjectMapper.toDomain(municipalObjectRequest);
        municipalObject.setOwner(owner);

        // Crear el objeto municipal
        MunicipalObject createdObject = municipalObjectServicePort.createMunicipalObject(municipalObject);

        // Usar el mapper para convertir el objeto en un DTO de salida
        MunicipalObjectResponse responseDto = municipalObjectMapper.toResponse(createdObject);

        ApiResponse apiResponse = new ApiResponse("Municipal object created successfully", responseDto);
        return ResponseEntity.status(201).body(apiResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody @Valid MunicipalObjectRequest municipalObjectRequest) {
        User authenticatedUser = getAuthenticatedUser();

        // Validar la propiedad directamente a través del servicio
        municipalObjectServicePort.validateOwnership(id, authenticatedUser);

        MunicipalObject municipalObject = municipalObjectMapper.toDomain(municipalObjectRequest);
        MunicipalObject updatedObject = municipalObjectServicePort.updateMunicipalObject(id, municipalObject);
        MunicipalObjectResponse responseDto = municipalObjectMapper.toResponse(updatedObject);

        ApiResponse apiResponse = new ApiResponse("Municipal object updated successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User authenticatedUser = getAuthenticatedUser();
        municipalObjectServicePort.validateOwnership(id, authenticatedUser);
        municipalObjectServicePort.deleteMunicipalObject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        MunicipalObject municipalObject = municipalObjectServicePort.findMunicipalObjectById(id);
        MunicipalObjectResponse responseDto = municipalObjectMapper.toResponse(municipalObject);
        ApiResponse apiResponse = new ApiResponse("Municipal object found successfully", responseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/my-objects")
    public ResponseEntity<ApiResponse> findAllByOwner(@PageableDefault(size = 10) Pageable pageable) {
        User owner = getAuthenticatedUser();

        // Buscar todos los objetos municipales que pertenezcan al usuario autenticado con paginación
        Page<MunicipalObject> municipalObjectsPage = municipalObjectServicePort.findAllByOwner(owner.getId(), pageable);

        // Convertir cada objeto municipal en un DTO usando el mapper
        List<MunicipalObjectResponse> responseDtos = municipalObjectsPage.stream()
                .map(municipalObjectMapper::toResponse)
                .toList();

        // Crear una respuesta con los datos de la página
        Map<String, Object> response = new HashMap<>();
        response.put("objects", responseDtos);
        response.put("currentPage", municipalObjectsPage.getNumber());
        response.put("totalItems", municipalObjectsPage.getTotalElements());
        response.put("totalPages", municipalObjectsPage.getTotalPages());

        ApiResponse apiResponse = new ApiResponse("Municipal objects found", response);
        return ResponseEntity.ok(apiResponse);
    }
}
