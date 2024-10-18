package com.acl.municipalidad.user.infrastructure.adapter;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.port.UserRepositoryPort;
import com.acl.municipalidad.user.infrastructure.adapter.request.RegisterRequest;
import com.acl.municipalidad.user.infrastructure.entity.UserEntity;
import com.acl.municipalidad.user.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(RegisterRequest request) {
        UserEntity userEntity = new UserEntity(request);
        userJpaRepository.save(userEntity).toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream().map(UserEntity::toDomain).toList();
    }
}
