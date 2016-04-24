package org.javatigers.social.facebook.api.impl;

import org.springframework.social.ApiBinding;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.GraphApi;

public interface CustomeFacebook extends GraphApi, ApiBinding {
	
	/**
	 * API for performing operations on feeds.
	 * @return {@link FeedOperations}
	 */
	FeedOperations feedOperations();
	
	/**
	 * @return the application namespace that this FacebookTemplate was created for. May be null if no namespace was given.
	 */
	String getApplicationNamespace();
	
}
