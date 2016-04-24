package org.javatigers.social.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.javatigers.social.domain.Accounts;
import org.javatigers.social.domain.Permission;
import org.javatigers.social.domain.Role;
import org.javatigers.social.domain.SocialAccounts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SecurityAccountDetailsService implements UserDetailsService{
	
	private AccountsService accountsService;
	
	@Inject
	public SecurityAccountDetailsService (AccountsService accountsService) {
		this.accountsService = accountsService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException("Username is empty");
		}
		Accounts accounts = this.accountsService.findByUsername(username);
		
		if (accounts == null) {
			throw new UsernameNotFoundException("User name not found");
		}
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (Role role : accounts.getRoles()) {
			
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			
			for(Permission permission : role.getPermissions()) {
				
				grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
			}
		}
		
		return new SocialAccounts(accounts, grantedAuthorities);
	}

}
