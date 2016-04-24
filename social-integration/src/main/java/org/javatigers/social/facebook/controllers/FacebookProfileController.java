package org.javatigers.social.facebook.controllers;

import javax.inject.Inject;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/facebook")
public class FacebookProfileController {
	
	@Inject
	private ConnectionRepository connectionRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String profile (Model model) {
		
		Connection<Facebook> connection = this.connectionRepository.findPrimaryConnection(Facebook.class);
		
		if (connection == null) {
			return "redirect:/connect/facebook";
		}
		
		model.addAttribute("profile", connection.getApi().userOperations().getUserProfile());
		
		return "facebook/profile";
	}
}
