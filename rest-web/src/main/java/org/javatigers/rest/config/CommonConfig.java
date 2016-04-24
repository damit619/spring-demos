package org.javatigers.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"org.javatigers.dao", "org.javatigers.service"})
public class CommonConfig {

}
