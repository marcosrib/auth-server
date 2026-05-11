package com.br.auth_server.security.oidc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcLogoutAuthenticationToken;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.br.auth_server.security.query.OAuth2AuthorizationQueryService;

import java.util.List;

@Component
public class OidcRevokeAuthorizationsLogoutHandler implements LogoutHandler {

	private final OAuth2AuthorizationQueryService auth2AuthorizationQueryService;
	private final OAuth2AuthorizationService authorizationService;
	private final SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();

	public OidcRevokeAuthorizationsLogoutHandler(
			OAuth2AuthorizationQueryService auth2AuthorizationQueryService,
			OAuth2AuthorizationService authorizationService) {
		this.auth2AuthorizationQueryService = auth2AuthorizationQueryService;
		this.authorizationService = authorizationService;
		
	}

	@Override
	public void logout(HttpServletRequest request,
	                   HttpServletResponse response,
	                   @Nullable Authentication authentication) {
		if (authentication == null || authentication.getName() == null) {
			return;
		}

		performLogout(request, response, authentication);
		revokeAuthorizations(authentication);
	}

	private void performLogout(HttpServletRequest request, HttpServletResponse response,
	                           Authentication authentication) {
		OidcLogoutAuthenticationToken oidcLogoutAuthentication = (OidcLogoutAuthenticationToken) authentication;
		if (oidcLogoutAuthentication.isPrincipalAuthenticated()) {
			this.securityContextLogoutHandler.logout(request, response,
					(Authentication) oidcLogoutAuthentication.getPrincipal());
		}
	}

	private void revokeAuthorizations(@Nullable Authentication authentication) {
		String email = authentication.getName();
		List<String> authorizationIds = auth2AuthorizationQueryService.findAuthorizationIds(email);
		for (String authorizationId : authorizationIds) {
			OAuth2Authorization authorization = authorizationService.findById(authorizationId);
			if (authorization != null) {
				authorizationService.remove(authorization);
			}
		}
	}
}