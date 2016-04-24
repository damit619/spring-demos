package org.javatigers.social.facebook.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/facebook/albums")
public class FacebookAlbumsController {
	
	private static final Logger logger = LoggerFactory.getLogger(FacebookAlbumsController.class);
	
	private final Facebook facebook;
	
	@Inject
	public FacebookAlbumsController (Facebook facebook) {
		this.facebook = facebook;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showAlbums (Model model) {
		
		model.addAttribute("albums", facebook.mediaOperations().getAlbums());
		
		logger.debug("Albums fatched");
		
		return "facebook/albums";
	}
	
	@RequestMapping(value = "/{albumId}", method = RequestMethod.GET)
	public String showAlbum (@PathVariable("albumId") String albumId, Model model) {
		
		model.addAttribute("album", facebook.mediaOperations().getAlbum(albumId));
		
		model.addAttribute("photos", facebook.mediaOperations().getPhotos(albumId));
		
		logger.debug("Single album and all photos");
		
		return "facebook/album";
	}
}
