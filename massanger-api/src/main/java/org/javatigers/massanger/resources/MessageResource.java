package org.javatigers.massanger.resources;

import java.util.List;

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

@Path("/messages")
public class MessageResource {

	private MessageService messageService;

	public MessageResource() {
		messageService = new MessageService();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMasseges() {
		return messageService.getMessages();
	}

	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam(value = "messageId") Long messageId) {
		return messageService.getMessage(messageId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message) {
		return this.messageService.addMessage(message);
	}

	@PUT
	@Path(value = "/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam(value = "messageId") Long messageId, Message message) {
		message.setId(messageId);
		return this.messageService.updateMessage(message);
	}

	@DELETE
	@Path(value = "/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message removeMessage(@PathParam(value = "messageId") Long messageId) {
		return this.messageService.deleteMessage(messageId);
	}
}
