package com.acl.municipalidad.user.infrastructure.service;

import com.acl.municipalidad.security.JwtService;
import com.acl.municipalidad.user.domain.model.Role;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.repository.IUserRepository;
import com.acl.municipalidad.user.domain.service.IUserAuthenticationService;
import com.acl.municipalidad.user.domain.service.IUserQueryService;
import com.acl.municipalidad.user.domain.service.IUserRegistrationService;
import com.acl.municipalidad.user.domain.dto.request.AuthenticationRequest;
import com.acl.municipalidad.user.domain.dto.request.RegisterRequest;
import com.acl.municipalidad.user.domain.exceptions.EmailAlreadyExistsException;
import com.acl.municipalidad.user.domain.exceptions.EmailNotFoundException;
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
public class UserServiceImpl implements IUserRegistrationService, IUserAuthenticationService, IUserQueryService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void registerUser(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.ADMIN);
        userRepository.save(newUser);
    }

    @Override
    public String authenticationUser(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            var user = userJpaRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new EmailNotFoundException("User not found"));

            return jwtService.generateToken(user);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials", e);
        }
    }

}
