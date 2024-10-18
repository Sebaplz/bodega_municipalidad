package com.acl.municipalidad.user.infrastructure.entity;

import com.acl.municipalidad.user.domain.model.Role;
import com.acl.municipalidad.user.domain.model.User;
import com.acl.municipalidad.user.infrastructure.adapter.request.RegisterRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public UserEntity(RegisterRequest request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.role = Role.ADMIN;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User toDomain() {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setRole(Role.ADMIN);
        return user;
    }
}