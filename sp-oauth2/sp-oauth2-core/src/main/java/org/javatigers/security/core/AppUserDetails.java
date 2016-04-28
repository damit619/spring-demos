package org.javatigers.security.core;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * This class extend User from spring with helps spring security to identify our Lending Point users.
 * 
 * @author Amit Dhiman
 *
 */
public class AppUserDetails extends User {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -11929914367761547L;
	
	private Long id;
	
	/**
	 * Intialize {@link AppUserDetails} and call {@link User} cunstructer.
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @param authorities
	 */
	public AppUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public long getId() {
        return id;
    }
}
