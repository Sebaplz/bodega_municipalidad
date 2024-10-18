package com.acl.municipalidad.user.infrastructure.rest.controllers;

import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.domain.port.AuthServicePort;
import com.acl.municipalidad.user.domain.port.UserServicePort;
import com.acl.municipalidad.user.infrastructure.adapter.request.AuthenticationRequest;
import com.acl.municipalidad.user.infrastructure.adapter.request.RegisterRequest;
import com.acl.municipalidad.user.infrastructure.adapter.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
//TODO:Cambiar el CORS
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserServicePort userServicePort;
    private final AuthServicePort authServicePort;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userServicePort.findAll();
        return ResponseEntity.ok(new ApiResponse("Found!", users));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new ApiResponse(errorMessage, null));
        }
        try {
            userServicePort.registerUser(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), null));
        }
        ApiResponse response = new ApiResponse("User successfully registered", request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                String errorMessage = result.getAllErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                return ResponseEntity.badRequest().body(new ApiResponse(errorMessage, null));
            }

            String token = authServicePort.authenticateUser(request);
            ApiResponse response = new ApiResponse("Authentication successful", token);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid credentials", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An unexpected error occurred: " + e.getMessage(), null));
        }
    }


}