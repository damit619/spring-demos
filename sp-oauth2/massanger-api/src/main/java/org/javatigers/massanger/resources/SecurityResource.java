package org.javatigers.massanger.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class SecurityResource {
	
	@Path("admin")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed("admin")
	public String getAdminMassege() {
		return "Admin Message";
	}
	
	@Path("user")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed("user")
	public String getUserMassege() {
		return "User Message";
	}
}
