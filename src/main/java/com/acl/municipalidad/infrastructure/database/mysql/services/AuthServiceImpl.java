package com.acl.municipalidad.infrastructure.database.mysql.services;

import com.acl.municipalidad.application.AuthService;
import com.acl.municipalidad.domain.exceptions.EmailAlreadyExistsException;
import com.acl.municipalidad.domain.exceptions.EmailNotFoundException;
import com.acl.municipalidad.domain.response.AuthResponse;
import com.acl.municipalidad.domain.response.AuthenticationRequest;
import com.acl.municipalidad.domain.response.RegisterRequest;
import com.acl.municipalidad.infrastructure.database.mysql.entity.Role;
import com.acl.municipalidad.infrastructure.database.mysql.entity.User;
import com.acl.municipalidad.infrastructure.database.mysql.repository.UserRepository;
import com.acl.municipalidad.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        Optional<User> existUser = userRepository.findUserByEmail(request.getEmail());
        if (existUser.isPresent()) {
            throw new EmailAlreadyExistsException("El email ya está registrado!");
        }
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword())
            );
            User user = userOptional.get();
            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder().token(jwtToken).build();
        } else {
            throw new EmailNotFoundException("El email no está registrado!");
        }
    }
}
