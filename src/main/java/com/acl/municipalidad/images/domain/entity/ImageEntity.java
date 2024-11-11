package com.acl.municipalidad.images.domain.entity;

import com.acl.municipalidad.images.domain.model.Image;
import com.acl.municipalidad.items.domain.entity.ItemEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String url;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    public Image toDomain() {
        Image image = new Image();
        image.setId(id);
        image.setUrl(url);
        return image;
    }

    public static ImageEntity fromDomain(Image image, ItemEntity itemEntity) {
        return ImageEntity.builder()
                .id(image.getId())
                .url(image.getUrl())
                .item(itemEntity)
                .build();
    }


}
