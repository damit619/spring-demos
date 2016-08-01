package org.javatigers.security.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AppCORSFilter extends OncePerRequestFilter {
	
	@Value(value = "${cors.allowed.origins}")
    private String allowedOriginsStr;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	LOGGER.debug("Application allowedOriginsStr : {}", allowedOriginsStr);
    	String[] allowedOrigins = allowedOriginsStr.split(",");
    	//Fetching and Restricting Origins
        String origin = request.getHeader("Origin");
        String apiorigin = request.getHeader("apiOrigin");
        if(origin != null && ! origin.isEmpty()) { //If Origin is coming and not empty
        	if(Arrays.asList(allowedOrigins).contains(origin)) {//Origin is in the allowed list of origins
        		response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                if (request.getMethod().equalsIgnoreCase("OPTIONS")) { //If request for OPTIONS is coming
                	if (!handlePreFlight(request, response)) {
                		LOGGER.error("OPTIONS pre-flight failed");
                		StringBuilder sb = new StringBuilder();
                        sb.append("Not Allowed");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getOutputStream().write(sb.toString().getBytes());
                		return;
                	}
                } else {//set Access-Control-Expose-Headers to Expose only required headers
                    response.setHeader("Access-Control-Expose-Headers",
                            "Content-Type, X-Requested-With, Origin, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization," +
                                    "Host, Connection, CPUser-Agent, Accept, Referer, Accept-Encoding, Accept-Language");
                	}
        		} 
        	//for swagger
        	}	else if (StringUtils.isNotEmpty(apiorigin)) { //If Origin is coming and not empty
	            	if(Arrays.asList(allowedOrigins).contains(apiorigin)) {//Origin is in the allowed list of origins
	            		response.setHeader("Access-Control-Allow-Origin", apiorigin);
	                    response.setHeader("Access-Control-Allow-Credentials", "true");
	                    if (request.getMethod().equalsIgnoreCase("OPTIONS")) { //If request for OPTIONS is coming
	                    	if (!handlePreFlight(request, response)) {
	                    		LOGGER.error("OPTIONS pre-flight failed");
	                    		StringBuilder sb = new StringBuilder();
	                            sb.append("Not Allowed");
	                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                            response.getOutputStream().write(sb.toString().getBytes());
	                    		return;
	                    	}
	                    } else {//set Access-Control-Expose-Headers to Expose only required headers
	                        response.setHeader("Access-Control-Expose-Headers",
	                                "Content-Type, X-Requested-With, Origin, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization," +
	                                        "Host, Connection, CPUser-Agent, Accept, Referer, Accept-Encoding, Accept-Language");
	                    }
	        	} 
        } 
        else {//Null Origin case
	        	LOGGER.error("Null Origin is not allowed");
	        	StringBuilder sb = new StringBuilder();
	            sb.append("Null origin is not allowed");
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getOutputStream().write(sb.toString().getBytes());
	            return;
	        }
        filterChain.doFilter(request, response);
    }

    private boolean handlePreFlight(HttpServletRequest request, HttpServletResponse response) {

        String requestMethod = request.getHeader("Access-Control-Request-Method");
        String requestHeaders = request.getHeader("Access-Control-Request-Headers");

        if (requestMethod == null || (!requestMethod.equalsIgnoreCase("GET") && !requestMethod.equalsIgnoreCase("POST") && !requestMethod.equalsIgnoreCase("PUT") && !requestMethod.equalsIgnoreCase("DELETE") && !requestMethod.equalsIgnoreCase("OPTIONS"))) {
        	LOGGER.error("No valid request method");
            return false;
        }
        
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");

        if (requestHeaders != null && !requestHeaders.isEmpty()) {
            List<String> headers = Arrays.asList(requestHeaders.split(","));
            response.setHeader("Access-Control-Allow-Headers", org.springframework.util.StringUtils.collectionToCommaDelimitedString(headers));
        }
        response.setHeader("Access-Control-Max-Age", "3600"); //optional
        return true;
    }

}
