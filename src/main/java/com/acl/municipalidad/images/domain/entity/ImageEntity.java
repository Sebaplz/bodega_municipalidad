package com.acl.municipalidad.images.domain.entity;

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
    private String url;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
}
