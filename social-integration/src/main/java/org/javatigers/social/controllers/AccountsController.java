package org.javatigers.social.controllers;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import org.javatigers.social.Utils.SecureSignInUtils;
import org.javatigers.social.domain.Accounts;
import org.javatigers.social.enums.SocialMediaService;
import org.javatigers.social.exception.UsernameAlreadyExistException;
import org.javatigers.social.service.AccountsService;
import org.javatigers.social.validator.AccountsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping(value = "/account/register")
@SessionAttributes(types = Accounts.class)
public class AccountsController {

	private static final Logger logger = LoggerFactory
			.getLogger(AccountsController.class);

	private final AccountsService accountsService;

	private final AccountsValidator accountsValidator;

	private final ProviderSignInUtils providerSignInUtils;

	private MessageSource messageSource;

	@Inject
	public AccountsController(AccountsService accountsService,
			MessageSource messageSource, AccountsValidator accountsValidator) {
		this.accountsService = accountsService;
		this.providerSignInUtils = new ProviderSignInUtils();
		this.messageSource = messageSource;
		this.accountsValidator = accountsValidator;
	}

	@ModelAttribute("countries")
	public Map<String, String> countries(Locale currentLocale) {
		Map<String, String> countries = new TreeMap<String, String>();

		for (Locale locale : Locale.getAvailableLocales()) {
			countries.put(locale.getCountry(),
					locale.getDisplayCountry(currentLocale));
		}
		return countries;
	}

	@InitBinder("accounts")
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id");
		binder.addValidators(this.accountsValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String accountsForm(WebRequest request, Model model) {

		Connection<?> connection = providerSignInUtils
				.getConnectionFromSession(request);
		if (connection != null) {
			model.addAttribute(
					"message",
					StringUtils.capitalize(messageSource
							.getMessage("text.user.not.registered",
									new Object[] { connection.getKey()
											.getProviderId() }, Locale.ENGLISH)));
			model.addAttribute("accounts", accountForm(connection));

		} else {
			model.addAttribute("accounts", new Accounts());
		}
		return "register/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerAccounts(
			@ModelAttribute @Validated Accounts accounts,
			BindingResult formBinding, WebRequest request) {

		String view = null;

		if (formBinding.hasErrors()) {
			return "register/register";
		}

		try {

			this.accountsService.save(accounts);

			SecureSignInUtils.signIn(accounts);

			providerSignInUtils.doPostSignUp(accounts.getEmailAddress(),
					request);

			logger.debug("User {} has registred successfully",
					accounts.getUsername());

			view = "redirect:/";

		} catch (UsernameAlreadyExistException e) {
			logger.error("Duplicate user", e);
			formBinding.reject("user.duplicate.username",
					new Object[] { accounts.getUsername() }, null);
			view = "register/register";
		}

		return view;
	}

	/**
	 * Creates the form object used in the registration form.
	 * 
	 * @param connection
	 * @return If a user is signing in by using a social provider, this method
	 *         returns a form object populated by the values given by the
	 *         provider. Otherwise this method returns an empty form object
	 *         (normal form registration).
	 */
	private Accounts accountForm(Connection<?> connection) {
		Accounts account = new Accounts();

		UserProfile userProfile = connection.fetchUserProfile();
		account.setEmailAddress(userProfile.getEmail());
		account.setFirstName(userProfile.getFirstName());
		account.setLastName(userProfile.getLastName());
		account.setUsername(userProfile.getUsername());
		ConnectionKey connKey = connection.getKey();
		account.setSignInProvider(SocialMediaService.valueOf(connKey
				.getProviderId().toUpperCase()));
		logger.debug("Sign in providerUserId is {} and providerId {}", connKey
				.getProviderUserId(), connKey.getProviderId().toUpperCase());

		return account;
	}

}
