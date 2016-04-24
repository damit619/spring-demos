package org.javatigers.social.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
//Spring security needs UserDetailsService and SocialUserDetailsService implementations for configuration setup
@ComponentScan(basePackages = { "org.javatigers.social.service",
		"org.javatigers.social.validator" })
@Import(value = {WebMVCConfig.class, PersistenceConfig.class, SpringSecurityConfig.class, SocialConfig.class})
@PropertySource(value = { "classpath:/META-INF/db/jdbc-hibernate.properties",
"classpath:/META-INF/social/social-app.properties" })
public class SocialWebApplicationContextConfig {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer () {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public MessageSource messageSource () {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("i18n/messages");
		messageSource.setUseCodeAsDefaultMessage(Boolean.TRUE);
		return messageSource;
	}
	
}
