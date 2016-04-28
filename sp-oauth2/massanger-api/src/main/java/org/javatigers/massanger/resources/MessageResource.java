package org.javatigers.massanger.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.javatigers.massanger.domain.Message;
import org.javatigers.massanger.service.MessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Component
@Path("/messages")
@Api(value = "messages", description = "The Message resouece.",  authorizations = {@Authorization ("Required auth2 access token to call these services")})
public class MessageResource {

	private MessageService messageService;

	public MessageResource() {
		messageService = new MessageService();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("#oauth2.clientHasRole('ROLE_API')")
	@ApiOperation("Return all messages.")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success OK"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request") })
	public List<Message> getMasseges() {
		return messageService.getMessages();
	}

	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Indicators For Message Read")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success OK"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request") })
	public Message getMessage(@PathParam(value = "messageId") Long messageId) {
		return messageService.getMessage(messageId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Indicators For Add Message")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success OK"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request") })
	public Message addMessage(Message message) {
		return this.messageService.addMessage(message);
	}

	@PUT
	@Path(value = "/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Indicators For Update Message")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success OK"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request") })
	public Message updateMessage(@PathParam(value = "messageId") Long messageId, Message message) {
		message.setId(messageId);
		return this.messageService.updateMessage(message);
	}

	@DELETE
	@Path(value = "/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Indicators For Delete Message")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success OK"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request") })
	public Message removeMessage(@PathParam(value = "messageId") Long messageId) {
		return this.messageService.deleteMessage(messageId);
	}
}
