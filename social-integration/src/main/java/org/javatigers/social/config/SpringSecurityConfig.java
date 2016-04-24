package org.javatigers.social.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Spring Security ignores request to static resources such as CSS or JS
		// files.
		web.ignoring().antMatchers("/resources/**/*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Configure form based login
		http.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login/authenticate")
				.failureUrl("/login?error=nOk")
				// Configure logout
				.and()
				.logout()
				.deleteCookies("JSESSIONID")
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				// Configure URL based authorization;
				.and()
				.authorizeRequests()
				.antMatchers("/auth/**", "/login", "/signup/**","/signin/**",
						"/account/register/**").permitAll()
				// Protect rest of all URL
				.antMatchers("/**").authenticated()
				// Add SocialAuthenticationFilter to Spring Security's filter
				// chain
				.and().apply(new SpringSocialConfigurer());

	}

	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder)
			throws Exception {
		authBuilder.userDetailsService(this.userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public ShaPasswordEncoder passwordEncoder() {
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		return passwordEncoder;
	}

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.noOpText();
	}
}
