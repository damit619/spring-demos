package org.javatigers.social.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.javatigers.social.Utils.SocialSignInAdapter;
import org.javatigers.social.domain.SocialAccounts;
import org.javatigers.social.facebook.controllers.PostToWallAfterConnectInterceptor;
import org.javatigers.social.support.SecurityContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ReconnectFilter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.web.DisconnectController;

@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

	private static final Logger logger = LoggerFactory
			.getLogger(SocialConfig.class);

	@Inject
	private DataSource dataSource;

	/**
	 * Configures the connection factories for Facebook.
	 * 
	 * @param cfConfig
	 * @param env
	 */
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer config,
			Environment env) {
		config.addConnectionFactory(new FacebookConnectionFactory(env
				.getProperty("facebook.app.id"), env
				.getProperty("facebook.app.secret"), env
				.getProperty("facebook.app.namespace")));
	}

	/**
	 * The UserIdSource determines the account ID of the user. The example
	 * application uses the username as the account ID.
	 */
	@Override
	public UserIdSource getUserIdSource() {
		return new UserIdSource() {
			@Override
			public String getUserId() {

				SocialAccounts accounts = SecurityContextSupport
						.getUserDetails();

				/*
				 * Authentication authentication = SecurityContextHolder
				 * .getContext().getAuthentication();
				 */
				if (accounts == null) {
					throw new IllegalStateException(
							"Unable to get a ConnectionRepository: no user signed in");
				}
				logger.debug("userIdSource is : {}", accounts.getAccount()
						.getEmailAddress());
				return accounts.getAccount().getEmailAddress();
			}
		};
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {

		return new JdbcUsersConnectionRepository(this.dataSource,
				connectionFactoryLocator,
				/**
				 * The TextEncryptor object encrypts the authorization details
				 * of the connection. In our example, the authorization details
				 * are stored as plain text. DO NOT USE THIS IN PRODUCTION.
				 */
				Encryptors.noOpText());
	}

	//
	// API Binding Beans
	//
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository
				.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}

	/**
	 * This bean manages the connection flow between the account provider and in
	 * OUR application.
	 */
	//
	// Web Controller and Filter Beans
	//
	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {

		ConnectController connectController = new ConnectController(
				connectionFactoryLocator, connectionRepository);

		connectController
				.addInterceptor(new PostToWallAfterConnectInterceptor());
		return connectController;
	}

	@Bean
	public ProviderSignInController providerSignInController(
			ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository usersConnectionRepository) {
		ProviderSignInController socialSignInController = new ProviderSignInController(
				connectionFactoryLocator, usersConnectionRepository,
				new SocialSignInAdapter(new HttpSessionRequestCache()));
		socialSignInController.setSignUpUrl("/account/register");
		return socialSignInController;
	}

	@Bean
	public DisconnectController disconnectController(
			UsersConnectionRepository usersConnectionRepository, Environment env) {

		return new DisconnectController(usersConnectionRepository,
				env.getProperty("facebook.appSecret"));
	}

	@Bean
	public ReconnectFilter apiExceptionHandler(
			UsersConnectionRepository usersConnectionRepository,
			UserIdSource userIdSource) {

		return new ReconnectFilter(usersConnectionRepository, userIdSource);
	}
}
