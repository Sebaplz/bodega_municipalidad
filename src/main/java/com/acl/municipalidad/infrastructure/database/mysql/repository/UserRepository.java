package com.acl.municipalidad.infrastructure.database.mysql.repository;

import com.acl.municipalidad.infrastructure.database.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}