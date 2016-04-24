package org.javatigers.jaxrs.srv.ws.exceptions;

import javax.validation.ValidationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Service Exception Handler for REST Services
 * 
 * @author Amit Dhiman
 *
 */
@Provider
@Component("serviceExceptionMapper")
public class JAXRSServiceExceptionMapper implements ExceptionMapper<Exception>{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Response toResponse(Exception exception) {
		logger.info("Handling exception", exception);
		
		Response.Status errStatus = Response.Status.INTERNAL_SERVER_ERROR;// Default
		if (exception instanceof ValidationException) {
			errStatus = Response.Status.BAD_REQUEST;
        } else if (exception instanceof NullPointerException) {
        	errStatus = Response.Status.INTERNAL_SERVER_ERROR;
        }
		return Response.status(errStatus).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
	}
	
}
