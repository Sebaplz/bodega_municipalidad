package com.acl.municipalidad.user.infrastructure.service;

import com.acl.municipalidad.security.JwtService;
import com.acl.municipalidad.user.domain.model.Role;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.repository.IUserRepository;
import com.acl.municipalidad.user.domain.service.IUserService;
import com.acl.municipalidad.user.domain.dto.request.AuthenticationRequest;
import com.acl.municipalidad.user.domain.dto.request.RegisterRequest;
import com.acl.municipalidad.user.infrastructure.exceptions.EmailAlreadyExistsException;
import com.acl.municipalidad.user.infrastructure.exceptions.EmailNotFoundException;
import com.acl.municipalidad.user.domain.repository.IUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {
    private final IUserRepository IUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserJpaRepository IUserJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return IUserRepository.findByEmail(email);
    }

    @Override
    public void registerUser(RegisterRequest request) {
        Optional<User> existingUser = IUserRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.ADMIN);
        IUserRepository.save(newUser);
    }

    @Override
    public String authenticationUser(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            var user = IUserJpaRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new EmailNotFoundException("User not found"));

            return jwtService.generateToken(user);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials", e);
        }
    }

}
