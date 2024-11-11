package com.acl.municipalidad.images.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageRequest {
    @NotEmpty(message = "Name is required")
    @Size(min = 10, max = 100, message = "url must be between 10 and 100 characters long")
    private String url;
}
