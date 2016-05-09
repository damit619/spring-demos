package org.javatigers.jaxrs.srv.cxf.config;

import org.javatigers.jaxrs.srv.ws.JAXRSService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = {"org.javatigers.jaxrs.srv.ws.services"}, includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value=JAXRSService.class))
@ImportResource("classpath:/WEB-INF/cxf/jaxrs-cxf-servlet.xml")
public class JAXRSCXFConfig {
	
}
