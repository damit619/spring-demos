package org.lendingpoint.security.config;

import javax.sql.DataSource;

import org.lendingpoint.security.core.LandingPointTokenEnhancer;
import org.lendingpoint.security.core.UniqueAuthenticationKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@ImportResource("classpath:/context.xml")
@PropertySource(value = "classpath:/META-INF/env/config.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "org.javatigers.security.core")
@EnableWebSecurity
public class AppOAuth2Security {

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	@Bean
	public ClientDetailsService clientDetailsService(DataSource dataSource) {
		return new JdbcClientDetailsService(dataSource);
	}
	
	@Autowired
	@Qualifier("appUserDetailsService")
	private UserDetailsService appUserDetailsService;
	
	@Autowired
	public void init (AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(appUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder (10));
	}
	
	@Autowired
	@Bean
	public TokenStore tokenStore(DataSource dataSource) {
		JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
		jdbcTokenStore.setAuthenticationKeyGenerator(new UniqueAuthenticationKeyGenerator());
		return jdbcTokenStore;
	}

	@Autowired
	@Bean
	public DefaultTokenServices tokenServices(DataSource dataSource) {
		DefaultTokenServices services = new DefaultTokenServices();
		services.setClientDetailsService(clientDetailsService);
		services.setSupportRefreshToken(Boolean.TRUE);
		services.setTokenStore(tokenStore(dataSource));
		services.setTokenEnhancer(new LandingPointTokenEnhancer());
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
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("/META-INF/i18n/messages", "/META-INF/i18n/validation-messages");
		messageSource.setUseCodeAsDefaultMessage(Boolean.TRUE);
		return messageSource;
	}

}
