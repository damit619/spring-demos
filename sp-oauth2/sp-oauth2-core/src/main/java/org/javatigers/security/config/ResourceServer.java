package org.javatigers.security.config;

import org.javatigers.security.core.AppCORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * Resource server protect api resources.
 * 
 * @author amit.dhiman
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {
	
	/**
	 * Resource id.
	 */
	private static final String RESOURCE_ID = "com.lendingpoint.cp";
	
	/**
	 * CORS filter required origin to pass through.
	 */
	@Autowired
	private AppCORSFilter lendingPointCORSFilter;
	
	/**
	 * TokenService for token generations.
	 */
	@Autowired
	@Qualifier("defaultTokenServices")
	private ResourceServerTokenServices defaultTokenServices;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources)
			throws Exception {
		resources.tokenServices(defaultTokenServices).resourceId(RESOURCE_ID);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off	
			http
				//.addFilterBefore(lendingPointCORSFilter, ChannelProcessingFilter.class)
				.authorizeRequests()
				.expressionHandler(new OAuth2WebSecurityExpressionHandler())
				.antMatchers("/api/messages").hasRole("CREATE_USER")
				.antMatchers("/api/*").hasRole("API")
				.antMatchers("/index","/swagger-ui/**").permitAll()
				
			.and()
				.anonymous().disable()
				.csrf().disable()
				.exceptionHandling()
					.authenticationEntryPoint(oauthAuthenticationEntryPoint())
					.accessDeniedHandler(new OAuth2AccessDeniedHandler());
			// @formatter:on
	}
	
	/**
	 * AuthenticationEntryPoint bean declarations.
	 * 
	 * @return AuthenticationEntryPoint
	 */
	@Bean
	protected AuthenticationEntryPoint oauthAuthenticationEntryPoint() {
		OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
		entryPoint.setRealmName("Authorization");
		return entryPoint;
	}

}

