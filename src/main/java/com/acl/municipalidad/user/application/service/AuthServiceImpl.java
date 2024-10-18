package com.acl.municipalidad.user.application.service;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.port.AuthServicePort;
import com.acl.municipalidad.user.domain.port.UserServicePort;
import com.acl.municipalidad.user.infrastructure.adapter.request.AuthenticationRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServicePort {
    @Value("${secretKey}")
    private String jwtSecret;

    private final UserServicePort userServicePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String authenticateUser(AuthenticationRequest request) {
        Optional<User> foundUser = userServicePort.findByEmail(request.getEmail());
        if (foundUser.isPresent() && passwordEncoder.matches(request.getPassword(), foundUser.get().getPassword())) {
            return generateJwtToken(foundUser.get());
        }
        throw new RuntimeException("Invalid credentials");
    }

    private String generateJwtToken(User user) {
        // Expiración del token (1 día)
        long jwtExpirationMs = 86400000;
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
