package org.javatigers.social.bootstrap;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.javatigers.social.config.SocialWebApplicationContextConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class SocialIntegrationWebAppIntializer implements
		WebApplicationInitializer {

	private static final String DISPATCHER_NAME = "dispatcherServlet";

	private static final String CHARACTER_ENCODING_FILTER_NAME = "charEncodingFilter";

	// private static final String SITE_MESH_FILTER_NAME = "sitemesh";

	private static final String SPRING_SECURITY_FILTER_CHAIN_NAME = "springSecurityFilterChain";

	private static final String ENCODING = "UTF-8";

	private static final Class<?>[] ANNOTATED_CLASSES = { SocialWebApplicationContextConfig.class };

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		
		this.registerDispatherServlet(servletContext);
		
		this.registerCharacterEncodingFilter(servletContext);
		
		this.registerSpringSecurityFilterChain(servletContext);
		
		this.registerContextLoaderListener(servletContext);
	
	}

	private void registerDispatherServlet(ServletContext servletContext) {
	
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				DISPATCHER_NAME,
				new DispatcherServlet(this
						.createdWebApplicationContext(ANNOTATED_CLASSES)));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	private void registerCharacterEncodingFilter(ServletContext servletContext) {

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(ENCODING);
		characterEncodingFilter.setForceEncoding(Boolean.TRUE);

		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter(
				CHARACTER_ENCODING_FILTER_NAME, characterEncodingFilter);
		encodingFilter.addMappingForUrlPatterns(this.dispatcherTypes(),
				Boolean.TRUE, "/*");
	}

	private void registerSpringSecurityFilterChain(ServletContext servletContext) {

		FilterRegistration.Dynamic springSecurity = servletContext.addFilter(
				SPRING_SECURITY_FILTER_CHAIN_NAME, new DelegatingFilterProxy());
		springSecurity.addMappingForUrlPatterns(this.dispatcherTypes(),
				Boolean.TRUE, "/*");
	}

	private EnumSet<DispatcherType> dispatcherTypes() {

		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(
				DispatcherType.REQUEST, DispatcherType.FORWARD);
		return dispatcherTypes;
	}

	private void registerContextLoaderListener(ServletContext servletContext) {

		servletContext.addListener(new ContextLoaderListener(this
				.createdWebApplicationContext(ANNOTATED_CLASSES)));
	}

	/**
	 * Factory method to create {@link WebApplicationContext} instances.
	 * 
	 * @param annotatedClasses
	 * @return
	 */
	private WebApplicationContext createdWebApplicationContext(
			Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(annotatedClasses);
		return webApplicationContext;
	}
}
