package org.javatigers.social.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.javatigers.social.domain.Accounts;
import org.javatigers.social.domain.Permission;
import org.javatigers.social.domain.Role;
import org.javatigers.social.domain.SocialAccounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SocialAccountDetailsService implements SocialUserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(SocialAccountDetailsService.class);
	
	private final AccountsService accountsService;
	
	@Inject
	public SocialAccountDetailsService (AccountsService accountsService) {
		this.accountsService = accountsService;
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId)
			throws UsernameNotFoundException, DataAccessException {
		if (StringUtils.isEmpty(userId)) {
			throw new UsernameNotFoundException("Username is empty");
		}
		
		logger.debug("userId in SocialAccountDetailsService {}", userId);
		
		Accounts accounts = this.accountsService.findByEmailAddress(userId);
		
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
		return (SocialUserDetails) new SocialAccounts(accounts, grantedAuthorities);
	}

}
