package com.acl.municipalidad.objects.infrastructure.rest.controller;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectServicePort;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.port.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/municipal-objects")
@RequiredArgsConstructor
public class MunicipalObjectController {

    private final MunicipalObjectServicePort municipalObjectServicePort;
    private final UserServicePort userServicePort;

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

    private void validateOwnership(Long objectId, User authenticatedUser) {
        MunicipalObject existingObject = municipalObjectServicePort.findMunicipalObjectById(objectId);
        if (!existingObject.getOwner().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("User does not have permission to modify this object");
        }
    }


    @PostMapping
    public ResponseEntity<MunicipalObject> create(@RequestBody MunicipalObject municipalObject) {
        // Usar el método auxiliar para obtener el usuario autenticado
        User owner = getAuthenticatedUser();

        // Asignar el usuario como propietario del objeto municipal
        municipalObject.setOwner(owner);

        MunicipalObject createdObject = municipalObjectServicePort.createMunicipalObject(municipalObject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdObject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MunicipalObject> update(@PathVariable Long id, @RequestBody MunicipalObject municipalObject) {
        // Obtener el usuario autenticado
        User authenticatedUser = getAuthenticatedUser();

        // Buscar el objeto municipal y verificar si el usuario autenticado es el propietario
        validateOwnership(id, authenticatedUser);

        // Si el usuario autenticado es el propietario, proceder con la actualización
        MunicipalObject updatedObject = municipalObjectServicePort.updateMunicipalObject(id, municipalObject);
        return ResponseEntity.ok(updatedObject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Obtener el usuario autenticado
        User authenticatedUser = getAuthenticatedUser();

        // Buscar el objeto municipal y verificar si el usuario autenticado es el propietario
        validateOwnership(id, authenticatedUser);

        // Si el usuario autenticado es el propietario, proceder con la eliminación
        municipalObjectServicePort.deleteMunicipalObject(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<MunicipalObject> findById(@PathVariable Long id) {
        MunicipalObject municipalObject = municipalObjectServicePort.findMunicipalObjectById(id);
        return ResponseEntity.ok(municipalObject);
    }

    @GetMapping("/my-objects")
    public ResponseEntity<List<MunicipalObject>> findAllByOwner() {
        // Usar el método auxiliar para obtener el usuario autenticado
        User owner = getAuthenticatedUser();

        List<MunicipalObject> municipalObjects = municipalObjectServicePort.findAllByOwner(owner.getId());

        return ResponseEntity.ok(municipalObjects);
    }
}
