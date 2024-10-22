package com.acl.municipalidad.objects.application.service;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectRepositoryPort;
import com.acl.municipalidad.objects.domain.port.MunicipalObjectServicePort;
import com.acl.municipalidad.objects.infrastructure.adapter.request.MunicipalObjectRequest;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MunicipalObjectServiceImpl implements MunicipalObjectServicePort {
    private final MunicipalObjectRepositoryPort municipalObjectRepository;
    private final UserRepositoryPort userRepository;


    @Override
    public MunicipalObject createMunicipalObject(MunicipalObject municipalObject) {
        return municipalObjectRepository.save(municipalObject);
    }

    @Override
    public MunicipalObject updateMunicipalObject(Long id, MunicipalObject municipalObject) {
        MunicipalObject existingObject = municipalObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Object not found"));

        existingObject.setName(municipalObject.getName());
        existingObject.setDescription(municipalObject.getDescription());
        existingObject.setAvailableQuantity(municipalObject.getAvailableQuantity());

        return municipalObjectRepository.save(existingObject);
    }

    @Override
    public void deleteMunicipalObject(Long id) {
        municipalObjectRepository.deleteById(id);
    }

    @Override
    public MunicipalObject findMunicipalObjectById(Long id) {
        return municipalObjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Object not found"));
    }

    @Override
    public List<MunicipalObject> findAllByOwner(Long ownerId) {
        return municipalObjectRepository.findByOwnerId(ownerId);
    }
}

