package com.acl.municipalidad.items.domain.entity;

import com.acl.municipalidad.items.domain.model.Item;
import com.acl.municipalidad.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    // Métodos de conversión para el dominio
    public Item toDomain() {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setAvailableQuantity(availableQuantity);
        item.setOwner(owner.toDomain());
        return item;
    }

    public static ItemEntity fromDomain(Item item) {
        return ItemEntity.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .availableQuantity(item.getAvailableQuantity())
                .owner(UserEntity.fromDomain(item.getOwner()))
                .build();
    }
}