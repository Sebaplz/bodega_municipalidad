package com.acl.municipalidad.user.infrastructure.adapter;

import com.acl.municipalidad.user.domain.port.AuthServicePort;
import com.acl.municipalidad.user.infrastructure.adapter.request.AuthenticationRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtAuthServiceAdapter implements AuthServicePort {
    @Value("${secretKey}")
    private String jwtSecret;

    @Override
    public String authenticateUser(AuthenticationRequest request) {
        return Jwts.builder()
                .setSubject(request.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token válido por 1 día
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
