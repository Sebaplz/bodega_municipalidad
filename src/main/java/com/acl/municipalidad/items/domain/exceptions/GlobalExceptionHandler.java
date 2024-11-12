package com.acl.municipalidad.items.domain.exceptions;

import com.acl.municipalidad.images.domain.exceptions.ImageLimitExceededException;
import com.acl.municipalidad.items.domain.dto.response.ApiResponse;
import com.acl.municipalidad.user.domain.exceptions.EmailAlreadyExistsException;
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

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExists(EmailAlreadyExistsException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(SpecialItemException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(SpecialItemException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse> handleUnauthorized(UnauthorizedException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(ImageLimitExceededException.class)
    public ResponseEntity<ApiResponse> handleImageLimitExceeded(ImageLimitExceededException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
}
