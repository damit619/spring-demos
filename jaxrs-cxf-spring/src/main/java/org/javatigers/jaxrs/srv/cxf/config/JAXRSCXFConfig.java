package org.javatigers.jaxrs.srv.cxf.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ValidatorFactory;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.impl.WebApplicationExceptionMapper;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.metrics.MetricsFeature;
import org.apache.cxf.validation.BeanValidationFeature;
import org.apache.cxf.validation.BeanValidationProvider;
import org.javatigers.jaxrs.srv.ws.JAXRSService;
import org.javatigers.jaxrs.srv.ws.exceptions.JAXRSServiceExceptionMapper;
import org.javatigers.jaxrs.srv.ws.services.Message10RS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Configuration
@ComponentScan(basePackages = {"org.javatigers.jaxrs.srv.ws.services"}, includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value=JAXRSService.class))
public class JAXRSCXFConfig {
	
	@Autowired
	private JAXRSServiceExceptionMapper serviceExceptionMapper;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ValidatorFactory validatorFactory;
	
	@Autowired
	private Message10RS message10RS;
	
	@Bean(name = "cxf", destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}
	
	@DependsOn("cxf")
	@Bean 
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
		factory.setServiceBeans( Arrays.< Object >asList(message10RS));
		factory.setAddress( factory.getAddress() );
		factory.setFeatures(getFeatures2 ());
		factory.setProviders( getProviders () );
		return factory.create();
	}
	
	/**
	 * support swagger 2.0 spec https://issues.apache.org/jira/browse/CXF-6555
	 * 
	 * @return Swagger2Feature
	 */
	@Bean 
	public Swagger2Feature swagger2Feature() {
		final Swagger2Feature swagger2Feature = new Swagger2Feature();
		//run as filter so it is not denied by our own access filters
		swagger2Feature.setRunAsFilter(Boolean.TRUE);
		swagger2Feature.setBasePath("jaxrs-cxf/api/v1");
		swagger2Feature.setContact(environment.getProperty("swagger.contact"));
		swagger2Feature.setLicense("");
		swagger2Feature.setLicenseUrl("");
		swagger2Feature.setResourcePackage("org.javatigers.jaxrs.srv.ws.services");
		swagger2Feature.setTitle(environment.getProperty("swagger.title"));
		swagger2Feature.setDescription(environment.getProperty("swagger.description"));
		
		return swagger2Feature;
	}
	
	@Bean
	public BeanValidationFeature beanValidationFeature () {
		BeanValidationFeature beanValidationFeature = new BeanValidationFeature();
		beanValidationFeature.setProvider(new BeanValidationProvider(validatorFactory));
		return beanValidationFeature;
	}
	
	private List<Feature> getFeatures2 () {
		List<Feature> features2 = new ArrayList<>();
		LoggingFeature loggingFeature = new LoggingFeature();
		loggingFeature.setPrettyLogging(Boolean.FALSE);
		features2.add(new MetricsFeature());
		features2.add(loggingFeature);
		features2.add(swagger2Feature());
		features2.add(beanValidationFeature ());
		return features2;
	}
	
	private List<Object> getProviders () {
		List<Object> providers = new ArrayList<>();
		providers.add(serviceExceptionMapper);
		providers.add(webApplicationExceptionMapper ());
		providers.add(jacksonJaxbJsonProvider ());
		return providers;
	}
	
	@Bean
	public WebApplicationExceptionMapper webApplicationExceptionMapper () {
		WebApplicationExceptionMapper webApplicationExceptionMapper = new WebApplicationExceptionMapper();
		webApplicationExceptionMapper.setPrintStackTrace(Boolean.TRUE);
		webApplicationExceptionMapper.setAddMessageToResponse(Boolean.FALSE);
		return webApplicationExceptionMapper;
	}
	
	@Bean 
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
	
	@Bean
	public JacksonJaxbJsonProvider jacksonJaxbJsonProvider () {
		return new JacksonJaxbJsonProvider ();
	}
}
