package org.javatigers.social.domain;

import java.util.Collection;

import org.javatigers.social.enums.SocialMediaService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUserDetails;
/**
 * simple utility class that uses {@link User#getUsername()} as {@link SocialUserDetails#getUserId()} for SocialUserDetails
 * @author Amit Dhiman
 *
 */
public class SocialAccounts extends User implements SocialUserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 280545035072834562L;
	
	private Accounts account;

	private SocialMediaService signInProvider;

	public SocialAccounts(Accounts account,
			Collection<GrantedAuthority> grantedAuthorities) {
		
		super(account.getUsername(), account.getPassword(), account.isEnabled(), 
				true, true, true, grantedAuthorities);
		this.account = account;
		this.signInProvider = this.account.getSignInProvider();
		// erase password
		this.account.setPassword(null);
	
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}

	@Override
	public String getUserId() {
		return this.account.getEmailAddress();
	}
	
	public SocialMediaService getSignInProvider() {
		return this.signInProvider;
	}

	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}
	
}
