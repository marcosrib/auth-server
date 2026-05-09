package com.br.auth_server.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import com.br.auth_server.model.AuthUser;
import com.br.auth_server.repositories.AuthUserRepository;

@Service
public class OidcUserInfoService {
    
    final AuthUserRepository authUserRepository;

    public OidcUserInfoService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public OidcUserInfo loaUser(String email) {
        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return OidcUserInfo.builder()
                .subject(authUser.getId())
                .name(authUser.getName())
                .email(authUser.getEmail())
                .claim("type", authUser.getType().name())
                .claim("created_at", String.valueOf(authUser.getCreatedAt().toEpochSecond()))
                .build();   
    }
}
