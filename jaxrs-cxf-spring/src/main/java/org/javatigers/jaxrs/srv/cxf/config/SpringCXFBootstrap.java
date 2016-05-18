package org.javatigers.jaxrs.srv.cxf.config;

import java.util.EnumSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import ch.qos.logback.classic.selector.servlet.ContextDetachingSCL;
import ch.qos.logback.classic.selector.servlet.LoggerContextFilter;

public class SpringCXFBootstrap implements WebApplicationInitializer {
	
	private static final String DISPATCHER_SERVLET = "mvc";
	private static final String CXF_SERVLET = "JAXRS-CXF-Servlet";

	private static final Class<?>[] CONTEXT_ANNOTATED_CLASSES = { RootApplicationConfig.class, JAXRSCXFConfig.class};
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerLoggingComponents(servletContext);
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
	
	private void registerLoggerContextFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic loggerContextFilter = servletContext.addFilter("LoggerContextFilter", new LoggerContextFilter());
		loggerContextFilter.addMappingForUrlPatterns(this.dispatcherTypes(), Boolean.TRUE, "/*");
	}
	
	private void registerMDCInsertingServletFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic loggerContextFilter = servletContext.addFilter("MDCInsertingServletFilter", new MDCInsertingServletFilter());
		loggerContextFilter.addMappingForUrlPatterns(this.dispatcherTypes(), Boolean.TRUE, "/*");
	}
	
	/**
	 * Frees the logback logging context, see: http://logback.qos.ch/manual/loggingSeparation.html
	 * 
	 * @param servletContext
	 */
	private void registerContextDetachingSCL(ServletContext servletContext) {
		servletContext.addListener(new ContextDetachingSCL());
	}
	private void registerLoggingComponents (ServletContext servletContext) {
		addJNDILoggingContext();
		registerLoggerContextFilter(servletContext);
		registerMDCInsertingServletFilter(servletContext);
		registerContextDetachingSCL(servletContext);
	}
	private EnumSet<DispatcherType> dispatcherTypes() {

		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(
				DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR, DispatcherType.ASYNC);
		return dispatcherTypes;
	}
	/**
	 * 	<env-entry>
			<description>Logback JNDI logging context for this app</description>
			<env-entry-name>logback/context-name</env-entry-name>
			<env-entry-type>java.lang.String</env-entry-type>
			<env-entry-value>jaxrs-cxf</env-entry-value>
		</env-entry>
		<env-entry>
			<description>Logback URL for configuring logback context</description>
			<env-entry-name>logback/configuration-resource</env-entry-name>
			<env-entry-type>java.lang.String</env-entry-type>
			<env-entry-value>jaxrs-srv/logback.xml</env-entry-value>
		</env-entry>
	 */
	private void addJNDILoggingContext() {
		try {
			Context context = new InitialContext();
			context.addToEnvironment("logback/context-name", "JAXRS-CXF");
			context.addToEnvironment("logback/configuration-resource", "jaxrs-srv/logback.xml");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
}
