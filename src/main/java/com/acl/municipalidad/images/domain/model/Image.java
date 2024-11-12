package com.acl.municipalidad.images.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Long id;
    private String url;
    private Long itemId;
}
