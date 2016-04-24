package org.javatigers.social.Utils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javatigers.social.service.AccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class SocialSignInAdapter implements SignInAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialSignInAdapter.class);
	
	private final RequestCache requestCache;
	
	@Autowired(required = true)
	private AccountsService accountsService;
	
	@Inject
	public SocialSignInAdapter (RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	
	@Override
	public String signIn(String localUserId, Connection<?> connection,
			NativeWebRequest nativeReq) {
		
		logger.debug("Sign in user from SocialSignInAdapter localUserId is {}", localUserId);
		
		SecureSignInUtils.signIn(this.accountsService.findByEmailAddress(localUserId));
		
		return extractOriginaldUrl (nativeReq);
	}
	
	private String extractOriginaldUrl (NativeWebRequest nativeReq) {
		
		HttpServletRequest request = nativeReq.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = nativeReq.getNativeResponse(HttpServletResponse.class);
		
		SavedRequest savedReq = requestCache.getRequest(request, response);
		
		if (savedReq == null) {
			return null;
		}
		requestCache.removeRequest(request, response);
		removeAuthenticationAttributes (request.getSession(false));
		return savedReq.getRedirectUrl();
	}
	
	private void removeAuthenticationAttributes (HttpSession session) {
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
}
