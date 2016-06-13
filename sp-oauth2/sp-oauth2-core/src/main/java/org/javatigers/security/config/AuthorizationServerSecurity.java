package org.javatigers.security.config;

import org.javatigers.security.core.AppCORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * As there is no extension point for adding custom filter (eg. CORS filter) in
 * {@link AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)}
 * So we need to extend {@link AuthorizationServerSecurityConfiguration#configure(HttpSecurity)} to add our custom CORS {@link AppCORSFilter}.
 * This will only work by removing @EnableAuthorizationServer 
 * 
 * @author Amit Dhiman
 *
 */
@Configuration
public class AuthorizationServerSecurity extends AuthorizationServerSecurityConfiguration {
	
	/**
	 * Custom CORS filter gets called once per request.
	 */
	@Autowired
	private AppCORSFilter appCORSFilter;
	
	/**
	 * {@inheritDoc}
	 *  Spring Security ignores request to static resources such as CSS or JS files.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/index", "/index.jsp", "/swagger-ui/**", "/api/v1/api-docs/**");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.addFilterAfter(appCORSFilter, BasicAuthenticationFilter.class);
	}
}
