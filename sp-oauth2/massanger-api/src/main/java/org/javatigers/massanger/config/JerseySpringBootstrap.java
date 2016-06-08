package org.javatigers.massanger.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.glassfish.jersey.servlet.ServletContainer;
import org.lendingpoint.security.config.LendingPointIntializer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class JerseySpringBootstrap implements WebApplicationInitializer {
	
	private static final String OAUTH2_DISPATCHER_SERVLET = "dispatcher";
	private static final String JERSEY_SERVLET = "jersey-servlet";
	private static final String JERSEY_RS_INIT_APP_NAME = "javax.ws.rs.Application";
	private static final String SPRING_SECURITY_FILTER_CHAIN_NAME = "springSecurityFilterChain";

	private static final Class<?>[] CONTEXT_ANNOTATED_CLASSES = { Jersey2SpringConfiguration.class, 
			SpringDispatcherConfig.class ,OAuth2CoreIntializer.class};
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerContextLoaderListener(servletContext);
		registerOAUTH2DispatherServlet(servletContext);
		registerSpringSecurityFilterChain(servletContext);
		registerJerseyServlet(servletContext);
	}
	
	/**
	 * OAUTH2 Spring Dispatcher servlet handels request for tokens.
	 * 
	 * @param servletContext
	 */
	private void registerOAUTH2DispatherServlet(ServletContext servletContext) {
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(OAUTH2_DISPATCHER_SERVLET, new DispatcherServlet());
		dispatcher.setInitParameter("contextConfigLocation", "");
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		dispatcher.addMapping("/swagger-ui/*");
	}
	
	/**
	 * Swagger Spring Dispatcher servlet handels request for REST API services documentation.
	 * 
	 * @param servletContext
	 *//*
	private void registerSwaggerDispatherServlet(ServletContext servletContext) {
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(SWAGGER_DISPATCHER_SERVLET, new DispatcherServlet(createdWebApplicationContext(SwaggerMVCConfig.class)));
		dispatcher.setInitParameter("contextConfigLocation", "");
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/swagger-ui/*");
	}*/
	
	/**
	 * Register Jersery servlet {@link ServletContainer}.
	 * 
	 * @param servletContext
	 */
	private void registerJerseyServlet(ServletContext servletContext) {
		ServletContainer servletContainer = new ServletContainer();
		ServletRegistration.Dynamic jerseyRegisteration = servletContext.addServlet(JERSEY_SERVLET, servletContainer);
		jerseyRegisteration.setInitParameter(JERSEY_RS_INIT_APP_NAME, MessageApiResourceConfig.class.getName());
		jerseyRegisteration.setLoadOnStartup(2);
		jerseyRegisteration.addMapping("/api/*");
	}

	private void registerSpringSecurityFilterChain(ServletContext servletContext) {
		DelegatingFilterProxy filter = new DelegatingFilterProxy(SPRING_SECURITY_FILTER_CHAIN_NAME);
		filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
		servletContext.addFilter(SPRING_SECURITY_FILTER_CHAIN_NAME, filter).addMappingForUrlPatterns(null, false, "/*");
	}

	private void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.addListener(new ContextLoaderListener(createdWebApplicationContext(CONTEXT_ANNOTATED_CLASSES)));
		servletContext.addListener(new RequestContextListener());
        // The following line is required to avoid having jersey-spring3 registering it's own Spring root context.
		servletContext.setInitParameter("contextConfigLocation", "");
	}

	/**
	 * Factory method to create {@link WebApplicationContext} instances.
	 * 
	 * @param annotatedClasses
	 * @return
	 */
	private WebApplicationContext createdWebApplicationContext(Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(annotatedClasses);
		return webApplicationContext;
	}


}
