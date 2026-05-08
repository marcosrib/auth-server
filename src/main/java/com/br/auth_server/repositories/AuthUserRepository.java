package com.br.auth_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.auth_server.model.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
    Optional<AuthUser> findByEmail(String email);
    
}
