package org.javatigers.jaxrs.srv.cxf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.javatigers.jaxrs.srv.ws.exceptions"})
public class ExceptionProviderConfig {

}
