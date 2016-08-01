package org.javatigers.jaxrs.srv.cxf.config;

import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.auth.OAuth2Definition;

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
@ComponentScan(basePackages = {
		"org.javatigers.jaxrs.srv.ws.services" }, includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = JAXRSService.class) )
public class JAXRSCXFConfig {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplicationContext context;

	@Autowired
	private JAXRSServiceExceptionMapper serviceExceptionMapper;

	@Autowired
	private Environment environment;

	@Autowired
	private ValidatorFactory validatorFactory;

	@Bean(name = "cxf", destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}

	@DependsOn("cxf")
	@Bean
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(),
				JAXRSServerFactoryBean.class);
		factory.setServiceBeans(getCXFRSServices());
		factory.setAddress(factory.getAddress());
		factory.setFeatures(getFeatures2());
		factory.setProviders(getProviders());
		return factory.create();
	}

	/**
	 * Get resources marked with @JAXRSService.
	 * 
	 * @return List <ResourceProvider>
	 */
	public List<Object> getCXFRSServices() {
		List<Object> serviceBeans = new ArrayList<>();
		for (String beanName : context.getBeanNamesForAnnotation(JAXRSService.class)) {
			logger.info("JAXRS resource name : {}", beanName);
			serviceBeans.add(context.getBean(beanName));
		}
		return serviceBeans;
	}

	/**
	 * support swagger 2.0 spec https://issues.apache.org/jira/browse/CXF-6555
	 * 
	 * @return Swagger2Feature
	 */
	@Bean
	public Swagger2Feature swagger2Feature() {
		final OAuth2Swagger2Feature swagger2Feature = new OAuth2Swagger2Feature();
		// run as filter so it is not denied by our own access filters
		swagger2Feature.setRunAsFilter(Boolean.TRUE);
		swagger2Feature.setBasePath("jaxrs-cxf/api");
		swagger2Feature.setContact(environment.getProperty("swagger.contact"));
		swagger2Feature.setLicense("");
		swagger2Feature.setLicenseUrl("");
		swagger2Feature.setResourcePackage("org.javatigers.jaxrs.srv.ws.services");
		swagger2Feature.setTitle(environment.getProperty("swagger.title"));
		swagger2Feature.setDescription(environment.getProperty("swagger.description"));
		return swagger2Feature;
	}

	public static class OAuth2Swagger2Feature extends Swagger2Feature {
		
		@Override
		protected void addSwaggerResource(Server server) {
			super.addSwaggerResource(server);
			
			BeanConfig scanner = (BeanConfig) ScannerFactory.getScanner();
			
			Swagger swagger = scanner.getSwagger();
			swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
			swagger.securityDefinition("Origin", new ApiKeyAuthDefinition("http://localhost", In.HEADER));
			swagger.securityDefinition("message_oauth",
					new OAuth2Definition().password("http://localhost:18080/jaxrs-cxf/oauth/token"));
		}
	}

	@Bean
	public BeanValidationFeature beanValidationFeature() {
		BeanValidationFeature beanValidationFeature = new BeanValidationFeature();
		beanValidationFeature.setProvider(new BeanValidationProvider(validatorFactory));
		return beanValidationFeature;
	}

	private List<Feature> getFeatures2() {
		List<Feature> features2 = new ArrayList<>();
		LoggingFeature loggingFeature = new LoggingFeature();
		loggingFeature.setPrettyLogging(Boolean.FALSE);
		features2.add(new MetricsFeature());
		features2.add(loggingFeature);
		features2.add(swagger2Feature());
		features2.add(beanValidationFeature());
		return features2;
	}

	private List<Object> getProviders() {
		List<Object> providers = new ArrayList<>();
		providers.add(serviceExceptionMapper);
		providers.add(webApplicationExceptionMapper());
		providers.add(jacksonJaxbJsonProvider());
		return providers;
	}

	@Bean
	public WebApplicationExceptionMapper webApplicationExceptionMapper() {
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
	public JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
		return new JacksonJaxbJsonProvider();
	}
}
