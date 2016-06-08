package org.javatigers.jaxrs.srv.ws.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.javatigers.jaxrs.srv.domain.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



/**
 * Acts as a front end for Message REST web services operations.
 * 
 * @author Amit Dhiman
 *
 */
@Path("messages")
@Consumes(value = { MediaType.APPLICATION_JSON })
@Produces(value = { MediaType.APPLICATION_JSON })
@Api(value = "v1/messages", description = "The message resource.")
public interface Message10RS {
	
	@GET
	@ApiOperation("Return all messages.")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success OK"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request") })
	List<Message> listMessage ();
	
	@Valid
	@GET
	@Path(value = "/{messageId}")
	@ApiOperation("Indicators For Message Read")
	@ApiParam(name = "messageId", required = true)
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "Success OK"), @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request") })
	Message readMessage (@NotNull @PathParam("messageId") Long messageId);
}
