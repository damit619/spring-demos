package org.javatigers.jaxrs.srv.cxf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration for OAUTH2 oprations.
 * 
 * @author amit.dhiman
 *
 */
@Configuration
@EnableWebMvc
public class SpringDispatcherConfig extends WebMvcConfigurerAdapter {
	
}
