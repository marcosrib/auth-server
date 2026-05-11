package com.br.auth_server.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import com.br.auth_server.security.query.OAuth2AuthorizationQueryService;

@Component
public class JdbcOAuth2AuthorizationRepository implements OAuth2AuthorizationQueryService {
    
    private final JdbcOperations jdbcOperations;
private static final String SQL = "SELECT id FROM oauth2_authorization WHERE principal_name = ?";

    public JdbcOAuth2AuthorizationRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

	@Override
	public List<String> findAuthorizationIds(String principalName) {
		return jdbcOperations.queryForList(SQL, String.class, principalName);
	}
}
