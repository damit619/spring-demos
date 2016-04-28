package org.lendingpoint.security.config;

import org.lendingpoint.security.core.LendingPointCORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE-1)
public class TokenEndpointSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AppCORSFilter appCORSFilter;
	
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(clientDetailsUserService());
	}
	
	// This is required for password grants, which we specify below as one of the
	// {@literal authorizedGrantTypes()}.
	@Autowired
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationManagerBuilder authenticationManagerBuilder) {
		return (authentication) -> authenticationManagerBuilder.getOrBuild().authenticate(authentication);
	}

	@Bean
	protected UserDetailsService clientDetailsUserService() {
		return new ClientDetailsUserDetailsService(clientDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.addFilterBefore(appCORSFilter, ChannelProcessingFilter.class)
		.anonymous().disable()
		.antMatcher("/oauth/token").authorizeRequests()
		.antMatchers(HttpMethod.POST, "/oauth/token")
		.fullyAuthenticated()
		.and()
			.httpBasic().authenticationEntryPoint(clientAuthenticationEntryPoint())
		.and()
			.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/token")).disable()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler())
				.authenticationEntryPoint(clientAuthenticationEntryPoint())
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// @formatter:on
	}
	
	@Bean
	protected AccessDeniedHandler accessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}

	@Bean
	protected AuthenticationEntryPoint clientAuthenticationEntryPoint() {
		OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
		entryPoint.setTypeName("Basic");
		entryPoint.setRealmName("Authorization/client");
		return entryPoint;
	}

}
