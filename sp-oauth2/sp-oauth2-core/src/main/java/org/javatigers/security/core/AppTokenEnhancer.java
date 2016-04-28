package org.javatigers.security.core;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * Implementation for TokenEnhancer which will be return to the client in oauth
 * token request.
 * 
 * @author Amit Dhiman
 *
 */
public class AppTokenEnhancer implements TokenEnhancer {
	
	/**
	 * roles as MAP key.
	 */
	private final String ROLE = "role";
	
	/**
	 * {@inheritDoc}
	 * Adding Role, which will be used by the client to represent which type of user has been logged in.
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
		result.setAdditionalInformation(Collections.singletonMap(ROLE, StringUtils.join(((AppUserDetails) authentication.getUserAuthentication().getPrincipal()).getAuthorities(), ",")));
		return result;
	}
}
