package com.acl.municipalidad.objects.infrastructure.adapter.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MunicipalObjectRequest {
    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters long")
    private String name;
    @Size(max = 1000, message = "Description must be at most 1000 characters long")
    private String description;
    @NotNull(message = "Available quantity is required")
    @Min(value = 1, message = "Available quantity must be at least 1")
    private Integer availableQuantity;
    private LocalDateTime creationDate;
}
