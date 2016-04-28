package org.javatigers.security.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * OAuth2AuthorizationServer Configurations.
 * 
 * @author amit.dhiman
 *
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	/**
	 * JDBC dataSource required for {@link JdbcClientDetailsService}
	 */
	@Autowired
	private DataSource dataSource;
	
	/**
	 * TokenService for token generations.
	 */
	@Autowired
	@Qualifier("defaultTokenServices")
	private AuthorizationServerTokenServices defaultTokenServices;
	
	/**
	 * OAuth manager builder to create new {@link AuthenticationManager}
	 */
	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;
	
	/**
	 * AccessDenied handler.
	 * @return AccessDeniedHandler
	 */
	@Bean
	protected AccessDeniedHandler accessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}
	
	/**
	 * {@link AuthenticationEntryPoint} for client.
	 * 
	 * @return
	 */
	@Bean
	protected AuthenticationEntryPoint clientAuthenticationEntryPoint() {
		OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
		entryPoint.setTypeName("Basic");
		entryPoint.setRealmName("Authorization/client");
		return entryPoint;
	}
	
	/**
	 * This method is here for demo to costmize the {@link TokenGranter} or add your own {@link TokenGranter}. 
	 * 
	 * @param endpoints
	 * @return
	 */
	private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
		List<TokenGranter> granters = new ArrayList<TokenGranter>(Arrays.asList(endpoints.getTokenGranter()));
		return new CompositeTokenGranter(granters);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// This is required for password grants, which we specify below as one
		// of the
		// {@literal authorizedGrantTypes()}.
		endpoints.authenticationManager((authentication) -> authenticationManagerBuilder.getOrBuild().authenticate(authentication))
		.tokenServices(defaultTokenServices)
		.approvalStoreDisabled()
		.allowedTokenEndpointRequestMethods(HttpMethod.POST)
		.tokenGranter(tokenGranter(endpoints));
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isFullyAuthenticated()")
			.accessDeniedHandler(accessDeniedHandler())
			.authenticationEntryPoint(clientAuthenticationEntryPoint());
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
		clients
			.withClientDetails(new JdbcClientDetailsService(dataSource));
		// @formatter:on
	}


}
