package org.javatigers.jaxrs.srv.cxf.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource(value = "classpath:/META-INF/srv/app-config.properties", ignoreResourceNotFound=true)
@EnableAspectJAutoProxy
public class MessagesPropertiesAndAOPConfig {
	
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
	 * app-config.properties contains commons tunable values.
	 * @return PropertySourcesPlaceholderConfigurer
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/**
	 * Register {@link MessageSource} get messages from property files.
	 * 
	 * @return {@link MessageSource} - 
	 * 									for internationalization of i18 messages.
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:/META-INF/i18n/messages");
		messageSource.setUseCodeAsDefaultMessage(Boolean.TRUE);
		return messageSource;
	}
}
