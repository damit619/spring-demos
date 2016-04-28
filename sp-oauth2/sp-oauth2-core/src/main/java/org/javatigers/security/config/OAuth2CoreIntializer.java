package org.javatigers.security.config;

import org.javatigers.security.dao.config.AppDBConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(value = {AppDBConfig.class})
public class OAuth2CoreIntializer {}
