package org.javatigers.security.core;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.javatigers.security.dao.users.UsersDAO;
import org.javatigers.security.model.users.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementing Spring Security Custom User Details Service to Load user with Authorities.
 * 
 * @author Amit Dhiman
 *
 */
@Service("appUserDetailsService")
public class AppUserDetailsService implements UserDetailsService {
	
	/**
	 * Log events.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailsService.class);
	
	/**
	 * {@link UsersDAO} injected to perform DB oprations.
	 */
	@Inject
	private UsersDAO usersDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = usersDAO.readUser(username);
        if(null == users) { 
        	LOGGER.error("User not found with username: {}", username);
        	throw new UsernameNotFoundException("User not found");
        }
		Collection<GrantedAuthority> authorities = users.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName()))
				.collect(Collectors.<GrantedAuthority> toList());
		LOGGER.debug("Username {} with authority : {}", username, authorities);
        return new AppUserDetails(users.getId(), users.getUsername(), users.getPassword(), authorities);
	}

}
