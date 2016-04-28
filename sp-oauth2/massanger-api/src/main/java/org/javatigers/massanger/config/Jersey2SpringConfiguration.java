package org.javatigers.massanger.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Jersey Spring configuration.
 * 
 * @author amit.dhiman
 *
 */
@Configuration
@ComponentScan(basePackages = { "org.javatigers.massanger.resources" })
public class Jersey2SpringConfiguration {
	
}
