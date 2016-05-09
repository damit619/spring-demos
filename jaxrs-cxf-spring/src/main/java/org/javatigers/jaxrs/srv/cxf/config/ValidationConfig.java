package org.javatigers.jaxrs.srv.cxf.config;

import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {
	
	@Autowired
	@Bean
	public ValidatorFactory validatorFactory (MessageSource messageSource) {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource);
		return validatorFactoryBean;
	}
	
	@Bean
	public SpelExpressionParser SpelExpressionParser() {
		return new SpelExpressionParser();
	}
}
