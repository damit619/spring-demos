package org.javatigers.social.facebook.controllers;

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/facebook/friends")
public class FacebookFriendsController {

	private final Facebook facebook;
	
	@Inject
	public FacebookFriendsController (Facebook facebook) {
		this.facebook = facebook;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showFriends (Model model) {
		
		model.addAttribute("friends", facebook.friendOperations().getFriendProfiles());
		
		return "facebook/friends";
	}
}
