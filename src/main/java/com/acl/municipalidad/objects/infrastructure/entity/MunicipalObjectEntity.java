package com.acl.municipalidad.objects.infrastructure.entity;

import com.acl.municipalidad.objects.domain.model.MunicipalObject;
import com.acl.municipalidad.user.infrastructure.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "municipal_objects")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalObjectEntity {
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
    public MunicipalObject toDomain() {
        MunicipalObject municipalObject = new MunicipalObject();
        municipalObject.setId(id);
        municipalObject.setName(name);
        municipalObject.setDescription(description);
        municipalObject.setAvailableQuantity(availableQuantity);
        municipalObject.setOwner(owner.toDomain());
        return municipalObject;
    }

    public static MunicipalObjectEntity fromDomain(MunicipalObject municipalObject) {
        return MunicipalObjectEntity.builder()
                .id(municipalObject.getId())
                .name(municipalObject.getName())
                .description(municipalObject.getDescription())
                .availableQuantity(municipalObject.getAvailableQuantity())
                .owner(UserEntity.fromDomain(municipalObject.getOwner()))
                .build();
    }
}