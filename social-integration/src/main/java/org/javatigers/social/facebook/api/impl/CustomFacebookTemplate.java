package org.javatigers.social.facebook.api.impl;

import static org.javatigers.social.facebook.api.impl.PagedListUtils.getPagedListParameters;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.OpenGraphOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.impl.json.FacebookModule;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class CustomFacebookTemplate extends AbstractOAuth2ApiBinding implements CustomeFacebook {
	
	private FeedOperations feedOperations;
	
	private OpenGraphOperations openGraphOperations;

	private ObjectMapper objectMapper;

	private String applicationNamespace;

	/**
	 * Create a new instance of FacebookTemplate.
	 * This constructor creates a new FacebookTemplate able to perform unauthenticated operations against Facebook's Graph API.
	 * Some operations do not require OAuth authentication. 
	 * For example, retrieving a specified user's profile or feed does not require authentication (although the data returned will be limited to what is publicly available). 
	 * A FacebookTemplate created with this constructor will support those operations.
	 * Those operations requiring authentication will throw {@link NotAuthorizedException}.
	 */
	public CustomFacebookTemplate() {
		initialize();		
	}

	/**
	 * Create a new instance of FacebookTemplate.
	 * This constructor creates the FacebookTemplate using a given access token.
	 * @param accessToken An access token given by Facebook after a successful OAuth 2 authentication (or through Facebook's JS library).
	 */
	public CustomFacebookTemplate (String accessToken) {
		this(accessToken, null);
	}
	
	public CustomFacebookTemplate (String accessToken, String applicationNamespace) {
		super(accessToken);
		this.applicationNamespace = applicationNamespace;
		initialize();
	}
	
	@Override
	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
	}

	
	
	public RestOperations restOperations() {
		return getRestTemplate();
	}
	
	public OpenGraphOperations openGraphOperations() {
		return openGraphOperations;
	}
	
	public String getApplicationNamespace() {
		return applicationNamespace;
	}
	
	// low-level Graph API operations
	public <T> T fetchObject(String objectId, Class<T> type) {
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId).build();
		return getRestTemplate().getForObject(uri, type);
	}

	public <T> T fetchObject(String objectId, Class<T> type, String... fields) {
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		if(fields.length > 0) {
			String joinedFields = join(fields);
			queryParameters.set("fields", joinedFields);
		}		
		return fetchObject(objectId, type, queryParameters);
	}

	public <T> T fetchObject(String objectId, Class<T> type, MultiValueMap<String, String> queryParameters) {
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId).queryParams(queryParameters).build();
		return getRestTemplate().getForObject(uri, type);
	}

	public <T> PagedList<T> fetchConnections(String objectId, String connectionType, Class<T> type, String... fields) {
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		if(fields.length > 0) {
			String joinedFields = join(fields);
			queryParameters.set("fields", joinedFields);
		}		
		return fetchConnections(objectId, connectionType, type, queryParameters);
	}

	public <T> PagedList<T> fetchConnections(String objectId, String connectionType, Class<T> type, MultiValueMap<String, String> queryParameters) {
		String connectionPath = connectionType != null && connectionType.length() > 0 ? "/" + connectionType : "";
		URIBuilder uriBuilder = URIBuilder.fromUri(GRAPH_API_URL + objectId + connectionPath).queryParams(queryParameters);
		JsonNode jsonNode = getRestTemplate().getForObject(uriBuilder.build(), JsonNode.class);
		return pagify(type, jsonNode);
	}

	public <T> PagedList<T> fetchPagedConnections(String objectId, String connectionType, Class<T> type, MultiValueMap<String, String> queryParameters) {
		String connectionPath = connectionType != null && connectionType.length() > 0 ? "/" + connectionType : "";
		URIBuilder uriBuilder = URIBuilder.fromUri(GRAPH_API_URL + objectId + connectionPath).queryParams(queryParameters);
		JsonNode jsonNode = getRestTemplate().getForObject(uriBuilder.build(), JsonNode.class);
		return pagify(type, jsonNode);
	}

	public <T> PagedList<T> fetchConnections(String objectId, String connectionType, Class<T> type, MultiValueMap<String, String> queryParameters, String... fields) {
		if(fields.length > 0) {
			String joinedFields = join(fields);
			queryParameters.set("fields", joinedFields);
		}
		return fetchPagedConnections(objectId, connectionType, type, queryParameters);
	}

	private <T> PagedList<T> pagify(Class<T> type, JsonNode jsonNode) {
		List<T> data = deserializeDataList(jsonNode.get("data"), type);
		if (jsonNode.has("paging")) {
			JsonNode pagingNode = jsonNode.get("paging");
			PagingParameters previousPage = getPagedListParameters(pagingNode, "previous");
			PagingParameters nextPage = getPagedListParameters(pagingNode, "next");
			return new PagedList<T>(data, previousPage, nextPage);
		}
		return new PagedList<T>(data, null, null);
	}

	public byte[] fetchImage(String objectId, String connectionType, ImageType type) {
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId + "/" + connectionType + "?type=" + type.toString().toLowerCase()).build();
		ResponseEntity<byte[]> response = getRestTemplate().getForEntity(uri, byte[].class);
		if(response.getStatusCode() == HttpStatus.FOUND) {
			throw new UnsupportedOperationException("Attempt to fetch image resulted in a redirect which could not be followed. Add Apache HttpComponents HttpClient to the classpath " +
					"to be able to follow redirects.");
		}
		return response.getBody();
	}
	
	@SuppressWarnings("unchecked")
	public String publish(String objectId, String connectionType, MultiValueMap<String, Object> data) {
		MultiValueMap<String, Object> requestData = new LinkedMultiValueMap<String, Object>(data);
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId + "/" + connectionType).build();
		Map<String, Object> response = getRestTemplate().postForObject(uri, requestData, Map.class);
		return (String) response.get("id");
	}
	
	public void post(String objectId, String connectionType, MultiValueMap<String, String> data) {
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId + "/" + connectionType).build();
		getRestTemplate().postForObject(uri, new LinkedMultiValueMap<String, String>(data), String.class);
	}
	
	public void delete(String objectId) {
		LinkedMultiValueMap<String, String> deleteRequest = new LinkedMultiValueMap<String, String>();
		deleteRequest.set("method", "delete");
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId).build();
		getRestTemplate().postForObject(uri, deleteRequest, String.class);
	}
	
	public void delete(String objectId, String connectionType) {
		LinkedMultiValueMap<String, String> deleteRequest = new LinkedMultiValueMap<String, String>();
		deleteRequest.set("method", "delete");
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId + "/" + connectionType).build();
		getRestTemplate().postForObject(uri, deleteRequest, String.class);
	}
	
	public void delete(String objectId, String connectionType, MultiValueMap<String, String> data) {
		data.set("method", "delete");
		URI uri = URIBuilder.fromUri(GRAPH_API_URL + objectId + "/" + connectionType).build();
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(data, new HttpHeaders());
		getRestTemplate().exchange(uri, HttpMethod.POST, entity, String.class);
	}
	
	// AbstractOAuth2ApiBinding hooks
	@Override
	protected OAuth2Version getOAuth2Version() {
		return OAuth2Version.DRAFT_10;
	}

	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
		restTemplate.setErrorHandler(new FacebookErrorHandler());
	}

	@Override
	protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
		MappingJackson2HttpMessageConverter converter = super.getJsonMessageConverter();
		objectMapper = new ObjectMapper();				
		objectMapper.registerModule(new FacebookModule());
		converter.setObjectMapper(objectMapper);		
		return converter;
	}
	
	// private helpers
	private void initialize() {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis();
	}
		
	private void initSubApis() {
		feedOperations = new CustomFeedTemplate(this, getRestTemplate(), objectMapper, isAuthorized());
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> deserializeDataList(JsonNode jsonNode, final Class<T> elementType) {
		try {
			CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, elementType);
			return (List<T>) objectMapper.reader(listType).readValue(jsonNode.toString()); // TODO: EXTREMELY HACKY--TEMPORARY UNTIL I FIGURE OUT HOW JACKSON 2 DOES THIS
		} catch (IOException e) {
			throw new UncategorizedApiException("facebook", "Error deserializing data from Facebook: " + e.getMessage(), e);
		}
	}
	
	private String join(String[] strings) {
		StringBuilder builder = new StringBuilder();
		if(strings.length > 0) {
			builder.append(strings[0]);
			for (int i = 1; i < strings.length; i++) {
				builder.append("," + strings[i]);
			}
		}
		return builder.toString();
	}

	@Override
	public FeedOperations feedOperations() {
		return feedOperations;
	}

}
