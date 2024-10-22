package com.acl.municipalidad.objects.application.service;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectRepositoryPort;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectServicePort;
import com.acl.municipalidad.objects.infrastructure.exceptions.ResourceNotFoundException;
import com.acl.municipalidad.objects.infrastructure.exceptions.UnauthorizedException;
import com.acl.municipalidad.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MunicipalObjectServiceImpl implements MunicipalObjectServicePort {
    private final MunicipalObjectRepositoryPort municipalObjectRepository;

    @Override
    public MunicipalObject createMunicipalObject(MunicipalObject municipalObject) {
        return municipalObjectRepository.save(municipalObject);
    }

    @Override
    public MunicipalObject updateMunicipalObject(Long id, MunicipalObject municipalObject) {
        MunicipalObject existingObject = municipalObjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Municipal object not found"));

        existingObject.setName(municipalObject.getName());
        existingObject.setDescription(municipalObject.getDescription());
        existingObject.setAvailableQuantity(municipalObject.getAvailableQuantity());

        return municipalObjectRepository.save(existingObject);
    }

    @Override
    public void deleteMunicipalObject(Long id) {
        MunicipalObject existingObject = municipalObjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Municipal object not found"));

        municipalObjectRepository.deleteById(id);
    }

    @Override
    public MunicipalObject findMunicipalObjectById(Long id) {
        return municipalObjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Municipal object not found"));
    }

    @Override
    public Page<MunicipalObject> findAllByOwner(Long ownerId, Pageable pageable) {
        return municipalObjectRepository.findAllByOwnerId(ownerId, pageable);
    }

    public void validateOwnership(Long objectId, User authenticatedUser) {
        MunicipalObject existingObject = municipalObjectRepository.findById(objectId)
                .orElseThrow(() -> new ResourceNotFoundException("Municipal object not found"));

        if (!existingObject.getOwner().getId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedException("User does not have permission to modify this object");
        }
    }

}
