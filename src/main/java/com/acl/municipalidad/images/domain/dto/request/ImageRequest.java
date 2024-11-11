package com.acl.municipalidad.images.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ImageRequest {
    @NotEmpty(message = "Url is required")
    @Size(min = 10, max = 100, message = "url must be between 10 and 100 characters long")
    private String url;

    public ImageRequest(String url) {
        this.url = url;
    }
}
