package org.javatigers.social.Utils;

import java.util.ArrayList;
import java.util.Collection;

import org.javatigers.social.domain.Accounts;
import org.javatigers.social.domain.Permission;
import org.javatigers.social.domain.Role;
import org.javatigers.social.domain.SocialAccounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Programmatically signs in the user with the given the user ID.
 */

public class SecureSignInUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(SecureSignInUtils.class);
	
	public static void signIn(Accounts accounts) {
		SocialAccounts socialAccounts = getSocialAccounts(accounts);
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(socialAccounts, null,
						socialAccounts.getAuthorities()));
		
		logger.debug("logged in user {}", socialAccounts.toString());
	}
	
	private static SocialAccounts getSocialAccounts(Accounts accounts) {
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (Role role : accounts.getRoles()) {
			
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			
			for(Permission permission : role.getPermissions()) {
				
				grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}
		SocialAccounts socialAccounts =new SocialAccounts(accounts, grantedAuthorities);
		return socialAccounts;
	}

}
