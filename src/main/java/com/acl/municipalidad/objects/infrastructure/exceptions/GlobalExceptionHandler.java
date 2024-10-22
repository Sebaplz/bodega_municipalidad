package com.acl.municipalidad.objects.infrastructure.exceptions;

import com.acl.municipalidad.objects.infrastructure.adapter.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Mapear los errores de validación
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // Crear un mapa que contenga los errores en el campo "errors"
        Map<String, Object> data = new HashMap<>();
        data.put("errors", errors);

        ApiResponse apiResponse = new ApiResponse("Validation failed", data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
