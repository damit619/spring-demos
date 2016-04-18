package org.javatigers.massanger.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.glassfish.jersey.servlet.ServletContainer;
import org.lendingpoint.security.config.LendingPointSecurityConfig;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Replacement for web.xml in servlet 3 spec. Intialize jersey servlet.
 * 
 * @author amit.dhiman
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)	
public class JerseySpringIntializer implements WebApplicationInitializer{
	
	private static final String JERSEY_SERVLET = "jersey-servlet";
	private static final String JERSEY_RS_INIT_APP_NAME = "javax.ws.rs.Application";
	private static final String SPRING_SECURITY_FILTER_NAME = "springSecurityFilterChain";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		registerContextLoaderListener(servletContext);
		registerSpringSecurityFilterChain(servletContext);
		registerJerseyServlet(servletContext);
	}
	
	/**
	 * Register Spring Security Filter chain {@link DelegatingFilterProxy}.
	 * 
	 * @param servletContext
	 */
	private void registerSpringSecurityFilterChain (ServletContext servletContext) {
		servletContext.addFilter(SPRING_SECURITY_FILTER_NAME, DelegatingFilterProxy.class)
	        .addMappingForUrlPatterns(EnumSet.<DispatcherType> of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
	}
	
	/**
	 * Register Jersery servlet {@link ServletContainer}.
	 * 
	 * @param servletContext
	 */
	private void registerJerseyServlet(ServletContext servletContext) {
		ServletRegistration.Dynamic jerseyRegisteration = servletContext.addServlet(
				JERSEY_SERVLET, ServletContainer.class);
		jerseyRegisteration.setInitParameter(JERSEY_RS_INIT_APP_NAME, MessageApiResourceConfig.class.getName());
		jerseyRegisteration.setLoadOnStartup(1);
		jerseyRegisteration.addMapping("/webapi/*");
	}
	
	/**
	 * Register Springs {@link ContextLoaderListener}
	 * 
	 * @param servletContext
	 */
	private void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.addListener(ContextLoaderListener.class);
		servletContext.setInitParameter(ContextLoader.CONTEXT_CLASS_PARAM, AnnotationConfigWebApplicationContext.class.getName());
		servletContext.setInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, LendingPointSecurityConfig.class.getName());
	}

}
