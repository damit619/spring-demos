package org.javatigers.social.facebook.controllers;

import org.javatigers.social.facebook.api.impl.CustomFacebookTemplate;
import org.javatigers.social.facebook.api.impl.CustomeFacebook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/facebook/feed")
public class FacebookFeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FacebookFeedController.class);
	private CustomeFacebook facebook;
	private String appSecret = "CAAEUzRtZAkAIBAOPnypikgoLgKxu1ofBD8QZA1CzMn1QsYQrUcowwJzZCAVKuy0gbKsdpfcyejtG4sIviQY7nJv3tjZCb5LYVrvdrTqkxZAokiC8k96rAYPxDlLRMh3pagseClHTlJ7itkaEr4PcspDWGpFi0dm9ZBM1PFACdsNenKyByv1vOVKcCRpsfGHNIiIubPHeK9URaJSF3ZCtVgk";
	
	public FacebookFeedController () {
		this.facebook = new CustomFacebookTemplate(appSecret, "test_integration_so");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showFeed (Model model) {
		
		PagedList<Post> pagedList = null;
		try {
			
			FeedOperations feedOperations = this.facebook.feedOperations();
			pagedList = feedOperations.getFeed();
			
		} catch (Exception e) {
			logger.error("Feed error : {}", e);
		}
		
		logger.debug("Your posts : {}", pagedList);
		model.addAttribute("feeds", pagedList);
		
		return "facebook/feed";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String postUpdate (String message) {
		
		this.facebook.feedOperations().updateStatus(message);
		
		return "redirect:/facebook/feed";
	}
	
	/*private Facebook newFbTemplate() {
		facebook = new FacebookTemplate(appSecret,
				"test_integration_so");
		try {
			Field field = facebook.getClass()
					.getDeclaredField("objectMapper");
			field.setAccessible(true);
			ObjectMapper objMapper = (ObjectMapper) field.get(facebook);
			objMapper.configure(
					DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,
					true);
			field.setAccessible(false);
			
		} catch (Exception e) {
			logger.debug("Error occured {}", e);
		}
		return facebook;
	}*/
}
