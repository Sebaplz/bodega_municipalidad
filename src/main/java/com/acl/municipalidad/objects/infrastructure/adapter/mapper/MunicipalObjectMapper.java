package com.acl.municipalidad.objects.infrastructure.adapter.mapper;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.infrastructure.adapter.request.MunicipalObjectRequest;
import com.acl.municipalidad.objects.infrastructure.adapter.response.MunicipalObjectResponse;
import org.springframework.stereotype.Component;

@Component
public class MunicipalObjectMapper {

    // Convertir MunicipalObject a MunicipalObjectResponse (DTO de salida)
    public MunicipalObjectResponse toResponse(MunicipalObject municipalObject) {
        return new MunicipalObjectResponse(
                municipalObject.getId(),
                municipalObject.getName(),
                municipalObject.getDescription(),
                municipalObject.getAvailableQuantity()
        );
    }

    // Convertir MunicipalObjectRequest (DTO de entrada) a MunicipalObject (dominio)
    public MunicipalObject toDomain(MunicipalObjectRequest municipalObjectRequest) {
        return MunicipalObject.builder()
                .name(municipalObjectRequest.getName())
                .description(municipalObjectRequest.getDescription())
                .availableQuantity(municipalObjectRequest.getAvailableQuantity())
                .build();
    }
}

