package org.javatigers.social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.javatigers.social.controllers",
		"org.javatigers.social.facebook.controllers" })
public class WebMVCConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**/*").addResourceLocations(
				"classpath:/META-INF/web-resources/");
	}

	// Ensure that requests made to static resources are delegated forward to
	// the
	// container’s default servlet.
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * Login view controller is responsible to display a login path to the user.
	 * 
	 * {@link ViewControllerRegistry}
	 */
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {

		registry.addViewController("/login").setViewName("account/login");
		registry.addViewController("/connecttofacebook").setViewName("connect/connectToFacebook");
		/*
		 * Redirects request forward to the registration page. This hack is
		 * required because there is no way to set the sign in url to the
		 * SocialAuthenticationFilter class. Another option is to move
		 * registration page to to url '/signup' but I did not want to do that
		 * because that url looks a bit ugly.
		 */
		// Got another way to do that in @see{ProviderSignInController}
		registry.addRedirectViewController("/signup", "/account/register");
		registry.addRedirectViewController("/signin", "/login");
	}

	// -- Start Locale Support (I18N) --//

	/**
	 * The {@link LocaleChangeInterceptor} allows for the locale to be changed.
	 * It provides a <code>paramName</code> property which sets the request
	 * parameter to check for changing the language, the default is
	 * <code>locale</code>.
	 * 
	 * @return the {@link LocaleChangeInterceptor}
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/**
	 * The {@link LocaleResolver} implementation to use. Specifies where to
	 * store the current selectd locale.
	 * 
	 * @return the {@link LocaleResolver}
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	/*@Bean
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

		Properties exceptionMappings = new Properties();
		exceptionMappings.put("java.lang.Exception", "error/error");
		exceptionMappings.put("java.lang.RuntimeException", "error/error");

		exceptionResolver.setExceptionMappings(exceptionMappings);

		Properties statusCodes = new Properties();
		statusCodes.put("error/404", "404");
		statusCodes.put("error/error", "500");

		exceptionResolver.setStatusCodes(statusCodes);

		return exceptionResolver;
	}
*/
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setOrder(2);
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}
	
	@Bean
	public ViewResolver tilesViewResolver () {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setOrder(1);
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer () {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/tiles/tiles-configuration.xml" });
		return tilesConfigurer;
	}
}
