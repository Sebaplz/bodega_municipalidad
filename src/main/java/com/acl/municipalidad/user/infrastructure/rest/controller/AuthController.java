package com.acl.municipalidad.user.infrastructure.rest.controller;

import com.acl.municipalidad.user.domain.service.IUserAuthenticationService;
import com.acl.municipalidad.user.domain.service.IUserRegistrationService;
import com.acl.municipalidad.user.domain.dto.request.AuthenticationRequest;
import com.acl.municipalidad.user.domain.dto.request.RegisterRequest;
import com.acl.municipalidad.user.domain.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final IUserRegistrationService userRegistrationService;
    private final IUserAuthenticationService userAuthenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterRequest request) {
        userRegistrationService.registerUser(request);
        return ResponseEntity.ok(new ApiResponse("User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        try {
            String jwt = userAuthenticationService.authenticationUser(request);
            return ResponseEntity.ok(new ApiResponse("User logged in successfully", jwt));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid credentials", null));
        }
    }
}