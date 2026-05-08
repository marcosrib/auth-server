package com.br.auth_server.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.auth_server.model.AuthUser;
import com.br.auth_server.repositories.AuthUserRepository;

@Service
public class AuthUserDetailService implements UserDetailsService {

    final AuthUserRepository authUserRepository;

    public AuthUserDetailService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return User
                .withUsername(email)
                .password(authUser.getPassword())
                .disabled(!authUser.isEnabled())
                .build();
    }

}
