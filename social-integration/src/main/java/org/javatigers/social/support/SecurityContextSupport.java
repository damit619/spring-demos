package org.javatigers.social.support;


import org.javatigers.social.domain.SocialAccounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This class serves as support class to provide easy access to {@link Authentication} 
 * in our case @{see SocialUserAccount}
 * @author amit.kumar1
 *
 */
public class SecurityContextSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityContextSupport.class);
	
	public static SocialAccounts getUserDetails () {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		SocialAccounts user = (SocialAccounts) authentication.getPrincipal();
		logger.debug("Authentication userId : {}", user.getUserId());
		
		return user;
	}
}
