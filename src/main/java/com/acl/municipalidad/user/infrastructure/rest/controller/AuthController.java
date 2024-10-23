package com.acl.municipalidad.user.infrastructure.rest.controller;

import com.acl.municipalidad.user.domain.port.UserServicePort;
import com.acl.municipalidad.user.infrastructure.adapter.request.AuthenticationRequest;
import com.acl.municipalidad.user.infrastructure.adapter.request.RegisterRequest;
import com.acl.municipalidad.user.infrastructure.adapter.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServicePort userServicePort;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterRequest request) {
        userServicePort.registerUser(request);
        return ResponseEntity.ok(new ApiResponse("User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        try {
            String jwt = userServicePort.authenticationUser(request);
            return ResponseEntity.ok(new ApiResponse("User logged in successfully", jwt));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid credentials", null));
        }
    }
}