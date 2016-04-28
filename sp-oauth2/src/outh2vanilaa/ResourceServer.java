package org.lendingpoint.security.config;

import javax.servlet.Filter;

import org.lendingpoint.security.core.LendingPointCORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
public class ResourceServer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("resourceFilter")
	private Filter resourceFilter;
	
	@Autowired
	private AppCORSFilter appCORSFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off	
		http.addFilterBefore(resourceFilter, AbstractPreAuthenticatedProcessingFilter.class)
			.addFilterBefore(appCORSFilter, ChannelProcessingFilter.class)
			.requestMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher("/oauth/**")))
			.authorizeRequests()
			.antMatchers("/api/**")
			.authenticated()
			.antMatchers("/api/messages")
			.hasRole("CREATE_USER")
			.antMatchers("/**").hasRole("API")
			.expressionHandler(new OAuth2WebSecurityExpressionHandler())
		.and()
			.anonymous().disable()
			.csrf().disable()
			.exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler())
				.authenticationEntryPoint(oauthAuthenticationEntryPoint());
				
		// @formatter:on
	}
	
	@Bean
	protected AuthenticationEntryPoint oauthAuthenticationEntryPoint() {
		OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
		entryPoint.setRealmName("Authorization");
		return entryPoint;
	}
	
}