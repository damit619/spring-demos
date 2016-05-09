package org.javatigers.jaxrs.srv.cxf.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringCXFBootstrap implements WebApplicationInitializer {
	
	private static final String DISPATCHER_SERVLET = "mvc";
	private static final String CXF_SERVLET = "JAXRS-CXF-Servlet";

	private static final Class<?>[] CONTEXT_ANNOTATED_CLASSES = { RootApplicationConfig.class};
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerContextLoaderListener(servletContext);
		registerCXFServlet(servletContext);
		registerSwaggerDispatherServlet(servletContext);
	}
	
	/**
	 * Swagger Spring Dispatcher servlet handels request for REST API services documentation.
	 * 
	 * @param servletContext
	 */
	private void registerSwaggerDispatherServlet(ServletContext servletContext) {
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET, new DispatcherServlet());
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/swagger-ui/*");
	}
	
	/**
	 * Register CXF servlet {@link CXFServlet}.
	 * 
	 * @param servletContext
	 */
	private void registerCXFServlet(ServletContext servletContext) {
		CXFServlet cxf = new CXFServlet();
		ServletRegistration.Dynamic jerseyRegisteration = servletContext.addServlet(CXF_SERVLET, cxf);
		jerseyRegisteration.setInitParameter("config-location", JAXRSCXFConfig.class.getName());
		jerseyRegisteration.setLoadOnStartup(1);
		jerseyRegisteration.addMapping("/api/*");
	}

	private void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.addListener(new ContextLoaderListener(createdWebApplicationContext(CONTEXT_ANNOTATED_CLASSES)));
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
