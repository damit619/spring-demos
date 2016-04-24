package org.javatigers.rest;

import java.util.Collection;
import java.util.Locale;

import org.javatigers.domain.Users;
import org.javatigers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller.
 */
@RestController
@RequestMapping(value = "/user")
public class MyRestController {

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyRestController.class);

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long home(Locale locale, @PathVariable Long id) {
		LOGGER.info("Welcome home! The client locale is {}.", locale);
		return userService.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public Users findPerson(Locale locale, @PathVariable Long id) {
		LOGGER.info("Welcome home! The client locale is {}.", locale);
		return this.userService.findById(id);
	}

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Users> findAllPersons(Locale locale) {
		LOGGER.info("Welcome home! The client locale is {}.", locale);
		return this.userService.findAll();
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public Users addUser(@RequestBody Users user) {
		LOGGER.info("{} successfully added.", user.getFirstName());
		Assert.notNull(user);
		Users newUser = null;
		if (user.getId() == null) {
			newUser = this.userService.save(user);
		} else {
			newUser = this.userService.update(user);
		}
		return newUser;
	}
	
}
