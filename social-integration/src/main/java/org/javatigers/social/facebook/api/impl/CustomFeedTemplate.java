package org.javatigers.social.facebook.api.impl;

import static org.javatigers.social.facebook.api.impl.PagedListUtils.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.PostData;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CustomFeedTemplate extends AbstractFacebookOperations implements FeedOperations {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomFeedTemplate.class);
	
	private static final PagingParameters FIRST_PAGE = new PagingParameters(25, null, null, null);

	private final GraphApi graphApi;
	
	private ObjectMapper objectMapper;
	
	private final RestTemplate restTemplate;

	public CustomFeedTemplate(GraphApi graphApi, RestTemplate restTemplate, ObjectMapper objectMapper, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.graphApi = graphApi;
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public PagedList<Post> getFeed() {
		return getFeed("me", FIRST_PAGE);
	}

	public PagedList<Post> getFeed(PagingParameters pagedListParameters) {
		return getFeed("me", pagedListParameters);
	}
	
	public PagedList<Post> getFeed(String ownerId) {
		return getFeed(ownerId, FIRST_PAGE);
	}
		
	public PagedList<Post> getFeed(String ownerId, PagingParameters pagedListParameters) {
		requireAuthorization();
		JsonNode responseNode = fetchConnectionList(GraphApi.GRAPH_API_URL + ownerId + "/feed", pagedListParameters);
		return deserializeList(responseNode, null, Post.class);
	}

	public PagedList<Post> getHomeFeed() {
		return getHomeFeed(FIRST_PAGE);
	}
	
	public PagedList<Post> getHomeFeed(PagingParameters pagedListParameters) {
		requireAuthorization();
		JsonNode responseNode = fetchConnectionList(GraphApi.GRAPH_API_URL + "me/home", pagedListParameters);
		return deserializeList(responseNode, null, Post.class);
	}

	public PagedList<Post> getStatuses() {
		return getStatuses("me", FIRST_PAGE);
	}
	
	public PagedList<Post> getStatuses(PagingParameters pagedListParameters) {
		return getStatuses("me", pagedListParameters);
	}

	public PagedList<Post> getStatuses(String userId) {
		return getStatuses(userId, FIRST_PAGE);
	}
	
	public PagedList<Post> getStatuses(String userId, PagingParameters pagedListParameters) {
		requireAuthorization();
		JsonNode responseNode = fetchConnectionList(GraphApi.GRAPH_API_URL + userId + "/statuses", pagedListParameters);
		return deserializeList(responseNode, "status", Post.class);
	}

	public PagedList<Post> getLinks() {
		return getLinks("me", FIRST_PAGE);
	}

	public PagedList<Post> getLinks(PagingParameters pagedListParameters) {
		return getLinks("me", pagedListParameters);
	}

	public PagedList<Post> getLinks(String ownerId) {
		return getLinks(ownerId, FIRST_PAGE);
	}
	
	public PagedList<Post> getLinks(String ownerId, PagingParameters pagedListParameters) {
		requireAuthorization();
		JsonNode responseNode = fetchConnectionList(GraphApi.GRAPH_API_URL + ownerId + "/links", pagedListParameters);
		return deserializeList(responseNode, "link", Post.class);
	}

	public PagedList<Post> getPosts() {
		return getPosts("me", FIRST_PAGE);
	}

	public PagedList<Post> getPosts(PagingParameters pagedListParameters) {
		return getPosts("me", pagedListParameters);
	}

	public PagedList<Post> getPosts(String ownerId) {
		return getPosts(ownerId, FIRST_PAGE);
	}
	
	public PagedList<Post> getPosts(String ownerId, PagingParameters pagedListParameters) {
		requireAuthorization();
		JsonNode responseNode = fetchConnectionList(GraphApi.GRAPH_API_URL + ownerId + "/posts", pagedListParameters);
		return deserializeList(responseNode, null, Post.class);
	}
	
	public Post getPost(String entryId) {
		requireAuthorization();
		ObjectNode responseNode = (ObjectNode) restTemplate.getForObject(GraphApi.GRAPH_API_URL + entryId, JsonNode.class);
		return deserializePost(null, Post.class, responseNode);
	}

	public String updateStatus(String message) {
		return post("me", message);
	}

	public String postLink(String message, FacebookLink link) {
		return postLink("me", message, link);
	}
	
	public String postLink(String ownerId, String message, FacebookLink link) {
		requireAuthorization();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.set("link", link.getLink());
		map.set("name", link.getName());
		map.set("caption", link.getCaption());
		map.set("description", link.getDescription());
		map.set("message", message);
		return graphApi.publish(ownerId, "feed", map);
	}
	
	public String post(PostData post) {
		requireAuthorization();
		return graphApi.publish(post.getTargetFeedId(), "feed", post.toRequestParameters());
	}
	
	public String post(String ownerId, String message) {
		requireAuthorization();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.set("message", message);
		return graphApi.publish(ownerId, "feed", map);
	}

	public void deletePost(String id) {
		requireAuthorization();
		graphApi.delete(id);
	}

	public PagedList<Post> searchPublicFeed(String query) {
		return searchPublicFeed(query, FIRST_PAGE);
	}
	
	public PagedList<Post> searchPublicFeed(String query, PagingParameters pagedListParameters) {
		String url = GraphApi.GRAPH_API_URL + "search?q={query}&type=post";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("query", query);
		if (pagedListParameters.getLimit() != null) {
			url += "&limit={limit}";
			params.put("limit", pagedListParameters.getLimit());
		}
		if (pagedListParameters.getSince() != null) {
			url += "&since={since}";
			params.put("since", pagedListParameters.getSince());
		}
		if (pagedListParameters.getUntil() != null) {
			url += "&until={until}";
			params.put("until", pagedListParameters.getUntil());
		}
		JsonNode responseNode = restTemplate.getForObject(url, JsonNode.class, params);
		return deserializeList(responseNode, null, Post.class);
	}
	
	public PagedList<Post> searchHomeFeed(String query) {
		return searchHomeFeed(query, FIRST_PAGE);
	}
	
	public PagedList<Post> searchHomeFeed(String query, PagingParameters pagedListParameters) {
		requireAuthorization();
		URIBuilder uriBuilder = URIBuilder.fromUri(GraphApi.GRAPH_API_URL + "me/home").queryParam("q", query);
		uriBuilder = appendPagedListParameters(pagedListParameters, uriBuilder);
		URI uri = uriBuilder.build();
		JsonNode responseNode = restTemplate.getForObject(uri, JsonNode.class);
		return deserializeList(responseNode, null, Post.class);
	}

	public PagedList<Post> searchUserFeed(String query) {
		return searchUserFeed("me", query, FIRST_PAGE);
	}

	public PagedList<Post> searchUserFeed(String query, PagingParameters pagedListParameters) {
		return searchUserFeed("me", query, pagedListParameters);
	}

	public PagedList<Post> searchUserFeed(String userId, String query) {
		return searchUserFeed(userId, query, FIRST_PAGE);
	}
	
	public PagedList<Post> searchUserFeed(String userId, String query, PagingParameters pagedListParameters) {
		requireAuthorization();
		URIBuilder uriBuilder = URIBuilder.fromUri(GraphApi.GRAPH_API_URL + userId + "/feed").queryParam("q", query);
		uriBuilder = appendPagedListParameters(pagedListParameters, uriBuilder);		
		URI uri = uriBuilder.build();
		JsonNode responseNode = restTemplate.getForObject(uri, JsonNode.class);
		return deserializeList(responseNode, null, Post.class);
	}
	
	public PagedList<Post> getCheckins() {
		return getCheckins(new PagingParameters(25, 0, null, null));
	}

	public PagedList<Post> getCheckins(PagingParameters pagedListParameters) {
		requireAuthorization();
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("with", "location");
		return graphApi.fetchConnections("me", "posts", Post.class, params);
	}

	public Post getCheckin(String checkinId) {
		requireAuthorization();
		return graphApi.fetchObject(checkinId, Post.class);
	}
	
	// private helpers
	
	private JsonNode fetchConnectionList(String baseUri, PagingParameters pagedListParameters) {
		URIBuilder uriBuilder = URIBuilder.fromUri(baseUri);
		uriBuilder = appendPagedListParameters(pagedListParameters, uriBuilder);
		URI uri = uriBuilder.build();
		JsonNode responseNode = restTemplate.getForObject(uri, JsonNode.class);
		return responseNode;
	}

	private <T> PagedList<T> deserializeList(JsonNode jsonNode, String postType, Class<T> type) {
		JsonNode dataNode = jsonNode.get("data");
		List<T> posts = new ArrayList<T>();
		for (Iterator<JsonNode> iterator = dataNode.iterator(); iterator.hasNext();) {
			T t =  deserializePost(postType, type, (ObjectNode) iterator.next());
			if (t == null) {
				continue;
			}
			posts.add(t);
		}
		if (jsonNode.has("paging")) {
			JsonNode pagingNode = jsonNode.get("paging");
			PagingParameters previousPage = getPagedListParameters(pagingNode, "previous");
			PagingParameters nextPage = getPagedListParameters(pagingNode, "next");
			return new PagedList<T>(posts, previousPage, nextPage);
		}
		
		return new PagedList<T>(posts, null, null);
	}

	private <T> T deserializePost(String postType, Class<T> type, ObjectNode node) {
		try {
			if (postType == null) {
				postType = determinePostType(node);
			}
			
			// Must have separate postType field for polymorphic deserialization. If we key off of the "type" field, then it will
			// be null when trying to deserialize the type property.
			node.put("postType", postType); // used for polymorphic deserialization
			node.put("type", postType); // used to set Post's type property
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
			ObjectReader reader = objectMapper.reader(type);
			return reader.readValue(node.toString()); // TODO: EXTREMELY HACKY--TEMPORARY UNTIL I FIGURE OUT HOW JACKSON 2 DOES THIS
		} catch (IOException shouldntHappen) {
			//try accepting empty value of Privacy as null
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			try {
				return objectMapper.reader(type).readValue(node.toString());
			} catch (IOException e) {
				logger.error("inner catch facebook", "Error deserializing " + postType + " post", shouldntHappen);
			}
			logger.error("outer catch facebook", "Error deserializing " + postType + " post", shouldntHappen);
		}
		return null;
	}

	private String determinePostType(ObjectNode node) {
		if (node.has("type")) {
			try {
				String type = node.get("type").textValue();
				PostType.valueOf(type.toUpperCase());
				return type;
			} catch (IllegalArgumentException e) {
				return "post";
			}
		}
		return "post";
	}
	
	private URIBuilder appendPagedListParameters(PagingParameters pagedListParameters,
			URIBuilder uriBuilder) {
		if (pagedListParameters.getLimit() != null) {
			uriBuilder = uriBuilder.queryParam("limit", String.valueOf(pagedListParameters.getLimit()));
		}
		if (pagedListParameters.getSince() != null) {
			uriBuilder = uriBuilder.queryParam("since", String.valueOf(pagedListParameters.getSince()));
		}
		if (pagedListParameters.getUntil() != null) {
			uriBuilder = uriBuilder.queryParam("until", String.valueOf(pagedListParameters.getUntil()));
		}
		return uriBuilder;
	}

}
