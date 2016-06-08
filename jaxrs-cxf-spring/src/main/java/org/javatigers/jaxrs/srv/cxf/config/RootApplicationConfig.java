package org.javatigers.jaxrs.srv.cxf.config;

import org.javatigers.security.config.OAuth2CoreIntializer;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { MessagesPropertiesAndAOPConfig.class, ValidationConfig.class, OAuth2CoreIntializer.class, ExceptionProviderConfig.class, JAXRSCXFConfig.class })
@ComponentScan(basePackages = { "org.javatigers.jaxrs.srv.service" })
public class RootApplicationConfig {
	
	@Value("${traceInterceptor.enterMessage}")
	private String enterMessage;
	
	@Value("${traceInterceptor.exitMessage}")
	private String exitMessage;
	
	@Value("${traceInterceptor.useDynamicLogger}")
	private String useDynamicLogger;
	private static final String TRACE_LOGGER_NAME = "TRACE";
	
	@Bean
	public CustomizableTraceInterceptor customizableTraceInterceptor() {
		CustomizableTraceInterceptor customizableTraceInterceptor = new CustomizableTraceInterceptor();
		customizableTraceInterceptor.setUseDynamicLogger(Boolean.parseBoolean(useDynamicLogger));
		customizableTraceInterceptor.setLoggerName(TRACE_LOGGER_NAME);
		customizableTraceInterceptor.setEnterMessage(enterMessage);
		customizableTraceInterceptor.setExitMessage(exitMessage);
		return customizableTraceInterceptor;
	}

	@Bean
	public Advisor traceMonitorAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("org.javatigers.jaxrs.srv.cxf.aop.AppPointcuts.tracePointcut()");
		return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
	}
}
