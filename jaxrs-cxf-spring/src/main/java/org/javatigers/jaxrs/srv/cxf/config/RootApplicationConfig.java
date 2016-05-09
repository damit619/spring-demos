package org.javatigers.jaxrs.srv.cxf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {MessagesPropertiesAndAOPConfig.class, ExceptionProviderConfig.class, ValidationConfig.class})
@ComponentScan(basePackages = { "org.javatigers.jaxrs.srv.service" })
public class RootApplicationConfig {
}
