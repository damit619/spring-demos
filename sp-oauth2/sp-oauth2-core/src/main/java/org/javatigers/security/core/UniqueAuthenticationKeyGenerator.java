package org.javatigers.security.core;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

/**
 * Implementation for AuthenticationKeyGenerator which will be used to generate a key for token using multiple values 
 * 
 */
public class UniqueAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

	private static final String CLIENT_ID = "client_id";

	private static final String SCOPE = "scope";

	private static final String USERNAME = "username";
	
	@Override
	public String extractKey(OAuth2Authentication authentication) {
		
		Map<String, String> values = new LinkedHashMap<String, String>();
		OAuth2Request authorizationRequest = authentication.getOAuth2Request();
		
		//Old check from DefaultAuthenticationKeyGenerator
		/*if (!authentication.isClientOnly()) {
			values.put(USERNAME, authentication.getName());
		}*/
		//New check to always include username to generate a key for token
		if(authentication.getName() != null) {
			values.put(USERNAME, authentication.getName());//Adding username always for token generation
		}
		
		values.put(CLIENT_ID, authorizationRequest.getClientId());
		
		if (authorizationRequest.getScope() != null) {
			values.put(SCOPE, OAuth2Utils.formatParameterList(authorizationRequest.getScope()));
		}
		
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}
}

