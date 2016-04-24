package org.javatigers.social.controllers;

import javax.inject.Inject;
import javax.inject.Provider;

import org.javatigers.social.domain.SocialAccounts;
import org.javatigers.social.support.SecurityContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final Provider<ConnectionRepository> connectionRepositoryProvider;
	
	@Inject
	public HomeController (Provider<ConnectionRepository> connectionRepositoryProvider) {
		
		this.connectionRepositoryProvider = connectionRepositoryProvider;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		SocialAccounts socialAccounts = SecurityContextSupport.getUserDetails();
		if (socialAccounts.getSignInProvider() == null) {
			logger.debug("Connection has exprired ask user to connect with social service provider");
			model.addAttribute("conn", true);
		} else {
				logger.debug("Connection not expired");
				model.addAttribute("conn", false);
		}
		
		model.addAttribute("connectionsToProvider", getConnectionRepository ());
		model.addAttribute(socialAccounts.getAccount());
		
		logger.debug("Authenticated user {}", socialAccounts.getAccount().getUsername());
		
		return "home";
	}
	
	private ConnectionRepository getConnectionRepository () {
		return this.connectionRepositoryProvider.get();
	}
}
