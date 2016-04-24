package org.javatigers.social.exception;

public class UsernameAlreadyExistException extends Exception {

	private static final long serialVersionUID = -6916067836291137459L;

	public UsernameAlreadyExistException() {
		super();
	}

	public UsernameAlreadyExistException(String username) {
		super("The username " + username + "already in use.");
	}

}
