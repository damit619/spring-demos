<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page session="false"%>

		<c:choose>
			<c:when test="${conn eq true}">
				<a href='<c:url value="/connecttofacebook" />'> <spring:message code="text.connect.to.facebook"/> </a>
			</c:when>
			<c:otherwise>
			
			
			</c:otherwise>
		</c:choose>
		
		<%-- <sec:authorize access="fullyAuthenticated">
				<form method="POST" action='<c:url value="/logout" />'>
				
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
					
					<input type='submit' value='<spring:message code="text.logout.button"/>' />
				
				</form>
		</sec:authorize> --%>
	
		<sec:authorize access="fullyAuthenticated">
			<%-- <sec:authentication property="principal.username" var="username"
				scope="request" /> --%>
				
			<div style="margin-left: 300px; margin-top: 30px">
				
				<a href='<c:url value="/facebook"/>'><spring:message code="text.your.profile"/></a><br/>
				
				<a href='<c:url value="/facebook/feed"/>' ><spring:message code="text.find.your.feeds"/></a> <br/>
				
				<a href='<c:url value="/facebook/friends"/>'><spring:message code="text.your.friend.list"/></a> <br/>
				
				<a href='<c:url value="/facebook/albums"/>'><spring:message code="text.your.albums"/></a>
			
			</div>
			
		</sec:authorize>
	
	
	
		<sec:authorize access="! authenticated">
		
			<a href='<c:url value="/login/authenticate" />'><spring:message code="text.login.button"/></a>
			
		</sec:authorize>
	
	