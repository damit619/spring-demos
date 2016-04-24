package org.javatigers.social.validator;

import org.javatigers.social.domain.Accounts;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("accountsValidator")
public class AccountsValidator implements Validator{

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (Accounts.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Accounts account = (Accounts) target;
		ValidationUtils.rejectIfEmpty(errors, "username", "required.field", new Object[] { "Username" });
		ValidationUtils.rejectIfEmpty(errors, "password", "required.field", new Object[] { "Password" });
		ValidationUtils.rejectIfEmpty(errors, "emailAddress", "required.field", new Object[] { "Email address" });
		ValidationUtils.rejectIfEmpty(errors, "firstName", "required.field", new Object[] { "First name" });
		ValidationUtils.rejectIfEmpty(errors, "lastName", "required.field", new Object[] { "Last name" });
        ValidationUtils.rejectIfEmpty(errors, "address.country", "required.field", new Object[] { "Country" });
        
        if (! errors.hasFieldErrors("emailAddress")) {
        	if (! account.getEmailAddress().matches(EMAIL_PATTERN)) {
        		errors.reject("email.invalid", new Object[] {account.getAddress()}, null);
        	}
        }
        
	}

}
