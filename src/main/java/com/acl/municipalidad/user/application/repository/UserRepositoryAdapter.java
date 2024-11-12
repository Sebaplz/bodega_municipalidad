package com.acl.municipalidad.user.application.repository;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.repository.IUserRepository;
import com.acl.municipalidad.user.domain.entity.UserEntity;
import com.acl.municipalidad.user.domain.repository.IUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryAdapter implements IUserRepository {
    private final IUserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        savedEntity.toDomain();
    }
}
