package com.acl.municipalidad.user.application.service;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.port.UserRepositoryPort;
import com.acl.municipalidad.user.domain.port.UserServicePort;
import com.acl.municipalidad.user.infrastructure.adapter.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServicePort {
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(request);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
