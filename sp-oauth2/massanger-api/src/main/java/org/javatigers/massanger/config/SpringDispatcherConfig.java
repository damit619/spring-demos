package org.javatigers.massanger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.swagger.jaxrs.config.BeanConfig;

@Configuration
@EnableWebMvc
public class SpringDispatcherConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/*").addResourceLocations(
				"classpath:/META-INF/resources/webjars/swagger-ui/2.1.3/");
	}
	
	@Bean
	public BeanConfig swaggerConfig() {
		final BeanConfig config = new BeanConfig();
		config.setVersion( "1.0.0" );
		config.setScan( true );
		config.setResourcePackage( "org.javatigers.massanger.resources" );
		config.setBasePath("massanger-api/api/v1");
		
		return config;
	}
}
