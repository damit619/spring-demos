package org.javatigers.massanger.config;


import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.javatigers.massanger.resources.MessageResource;
import org.javatigers.massanger.resources.MyResource;
import org.javatigers.massanger.resources.SecurityResource;

@ApplicationPath("/")
public class MessageApiResourceConfig extends ResourceConfig {

	public MessageApiResourceConfig() {
		super();
		register(SecurityResource.class);
		register(MyResource.class);
		register(MessageResource.class);
		register(RolesAllowedDynamicFeature.class);
	}

}
