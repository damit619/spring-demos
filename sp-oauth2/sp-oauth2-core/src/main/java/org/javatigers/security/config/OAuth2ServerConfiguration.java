package org.javatigers.security.config;

import javax.sql.DataSource;

import org.javatigers.security.core.AppTokenEnhancer;
import org.javatigers.security.core.UniqueAuthenticationKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * OAuth2 component initialization.
 * 
 * 
 * @author amit.dhiman
 *
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/env/config.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "org.javatigers.security.core")
@EnableWebSecurity
public class OAuth2ServerConfiguration {
	
	/**
	 * User details for authentication.
	 */
	@Autowired
	@Qualifier("appUserDetailsService")
	private UserDetailsService appUserDetailsService;
	
	/**
	 * Global {@link AuthenticationManager} will authenticate users and perform extra check on isActive on users.
	 * 
	 * @param builder
	 * @throws Exception
	 */
	@Autowired
	public void init(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(appUserDetailsService).passwordEncoder(new BCryptPasswordEncoder(10));
	}
	
	/**
	 * TokenStore implementations bean {@link JdbcTokenStore} stores token.
	 * 
	 * @param dataSource
	 * @return
	 */
	@Autowired
	@Bean
	public TokenStore tokenStore(DataSource dataSource) {
		JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
		jdbcTokenStore.setAuthenticationKeyGenerator(new UniqueAuthenticationKeyGenerator());
		return jdbcTokenStore;
	}
	
	/**
	 * OAuth default token service with some extensions.
	 * 
	 * Access token Validity to be 30 minutes
	 * {@link DefaultTokenServices#setAccessTokenValiditySeconds(int)}. Refresh
	 * token Validity to be 8 Hours
	 * {@link DefaultTokenServices#setRefreshTokenValiditySeconds(int)}.
	 * 
	 * Note: this bean is shared b/w Resource server and Authrization server.
	 * 
	 * @param dataSource
	 * @return DefaultTokenServices
	 */
	@Autowired
	@Primary
	@Bean(name = "defaultTokenServices")
	public DefaultTokenServices defaultTokenServices(TokenStore tokenStore) {
		DefaultTokenServices services = new DefaultTokenServices();
		services.setSupportRefreshToken(Boolean.TRUE);
		services.setTokenStore(tokenStore);
		services.setTokenEnhancer(new AppTokenEnhancer());
		services.setAccessTokenValiditySeconds(1800);
		services.setRefreshTokenValiditySeconds(28800);
		return services;
	}

	/**
	 * An object assignable to Spring's BeanFactoryPostProcessor
	 * interface(implementations of BeanFactoryPostProcessor) should be declared
	 * as static if @Bean annotation is present. This will result in a failure
	 * to process annotations such as
	 * 
	 * @Autowired, @Resource and @PostConstruct within the method's
	 * declaring @Configuration class. Add the 'static' modifier to this method
	 * to avoid these container lifecycle issues; see @Bean javadoc for complete
	 * details
	 * 
	 * @return PropertySourcesPlaceholderConfigurer
	 */
	/*@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}*/
	
	/**
	 * Register {@link MessageSource} get messages from property files.
	 * 
	 * @return {@link MessageSource} - 
	 * 									for internationalization of i18 messages.
	 */
	/*@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("/META-INF/i18n/messages", "/META-INF/i18n/validation-messages");
		messageSource.setUseCodeAsDefaultMessage(Boolean.TRUE);
		return messageSource;
	}*/

}
