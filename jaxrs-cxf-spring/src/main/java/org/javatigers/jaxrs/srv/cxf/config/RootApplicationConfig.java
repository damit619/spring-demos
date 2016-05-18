package org.javatigers.jaxrs.srv.cxf.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Import(value = { MessagesPropertiesAndAOPConfig.class, ExceptionProviderConfig.class, ValidationConfig.class })
@ComponentScan(basePackages = { "org.javatigers.jaxrs.srv.service" })
public class RootApplicationConfig {

	@Bean
	public CustomizableTraceInterceptor customizableTraceInterceptor() {
		CustomizableTraceInterceptor customizableTraceInterceptor = new CustomizableTraceInterceptor();
		customizableTraceInterceptor.setUseDynamicLogger(true);
		customizableTraceInterceptor.setEnterMessage("Entering $[methodName]($[arguments])");
		customizableTraceInterceptor.setExitMessage("Exiting  $[methodName](), returned $[returnValue]");
		return customizableTraceInterceptor;
	}

	@Bean
	public Advisor traceMonitorAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("org.javatigers.jaxrs.srv.service.AppPointcuts.tracePointcut()");
		return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
	}
}
