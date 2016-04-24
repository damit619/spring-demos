package org.javatigers.social.facebook.api.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;

class PagedListUtils {

	public static PagingParameters getPagedListParameters(JsonNode pagingNode, String pageKey) {
		if (pagingNode == null || pagingNode.get(pageKey) == null) {
			return null;
		}
		String pageNode = pagingNode.get(pageKey).textValue();
		String limitString = extractParameterValueFromUrl(pageNode, "limit");
		String sinceString = extractParameterValueFromUrl(pageNode, "since");
		String untilString = extractParameterValueFromUrl(pageNode, "until");
		String offsetString = extractParameterValueFromUrl(pageNode, "offset");
		String after = extractEncodedParameterValueFromUrl(pageNode, "after");
		String before = extractEncodedParameterValueFromUrl(pageNode, "before");
		
		return new PagingParameters(
				limitString != null ? Integer.valueOf(limitString) : null, 
				offsetString != null ? Integer.valueOf(offsetString) : null,
				sinceString != null ? Long.valueOf(sinceString) : null, 
				untilString != null ? Long.valueOf(untilString) : null,
				after, before);
	}

	public static MultiValueMap<String, String> getPagingParameters(PagingParameters pagedListParameters) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		if (pagedListParameters.getOffset() != null) {
			parameters.add("offset", String.valueOf(pagedListParameters.getOffset()));
		}
		if (pagedListParameters.getLimit() != null) {
			parameters.add("limit", String.valueOf(pagedListParameters.getLimit()));
		}
		if (pagedListParameters.getSince() != null) {
			parameters.add("since", String.valueOf(pagedListParameters.getSince()));
		}
		if (pagedListParameters.getUntil() != null) {
			parameters.add("until", String.valueOf(pagedListParameters.getUntil()));
		}
		if (pagedListParameters.getBefore() != null) {
			parameters.add("before", String.valueOf(pagedListParameters.getBefore()));
		}
		if (pagedListParameters.getAfter() != null) {
			parameters.add("after", String.valueOf(pagedListParameters.getAfter()));
		}
		return parameters;
	}

	private static String extractEncodedParameterValueFromUrl(String url, String paramName) {
		try {
			String value = extractParameterValueFromUrl(url, paramName);
			return value != null ? URLDecoder.decode(value, "UTF-8") : null;
		} catch (UnsupportedEncodingException e) {
			// shouldn't happen
			return null;
		}
	}
	
	private static String extractParameterValueFromUrl(String url, String paramName) {
		int queryStart = url.indexOf('?') >= 0 ? url.indexOf('?') : 0;
		int startPos = url.indexOf(paramName + "=", queryStart);
		if (startPos == -1) {
			return null;
		}
		int ampPos = url.indexOf("&", startPos);
		if (ampPos >= 0) {
			return url.substring(startPos + paramName.length() + 1, ampPos);
		}
		return url.substring(startPos + paramName.length() + 1);
	}

}
