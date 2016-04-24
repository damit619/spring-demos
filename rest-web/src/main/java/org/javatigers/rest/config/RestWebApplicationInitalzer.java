package org.javatigers.rest.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext; 
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class RestWebApplicationInitalzer implements WebApplicationInitializer{

	private static final Class<?>[] CLASSES = {WebConfiguration.class};
	private static final Class<?>[] SERVICE_CLASSES = {CommonConfig.class};
	private static final String DISPACHER_SERVLET_NAME = "dispatcher";
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerDispatcherServlet(servletContext);
		registerContextLoaderListener(servletContext);
	}
	
	private void registerDispatcherServlet(ServletContext servletContext) {
		WebApplicationContext webContext = createWebContext(CLASSES);
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		ServletRegistration.Dynamic servletRegDynamic = servletContext.addServlet(DISPACHER_SERVLET_NAME, dispatcherServlet);
		servletRegDynamic.setLoadOnStartup(1);
		servletRegDynamic.addMapping("/");
	}
	
	private void registerContextLoaderListener(ServletContext servletContext) {
		WebApplicationContext webContext = createWebContext(SERVICE_CLASSES);
		servletContext.addListener(new ContextLoaderListener(webContext));
	}
	
	/**
     * Factory method to create {@link WebApplicationContext} instances. 
     * @param annotatedClasses
     * @return
     */
	private WebApplicationContext createWebContext(Class<?>...annotatedClasses) {
		AnnotationConfigWebApplicationContext config = new AnnotationConfigWebApplicationContext();
		config.register(annotatedClasses);
		return config;
	}

}
