package org.javatigers.social.facebook.api.impl;

import org.springframework.social.MissingAuthorizationException;

public class AbstractFacebookOperations {
	private final boolean isAuthorized;

	public AbstractFacebookOperations(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}
	
	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException("facebook");
		}
	}
}
