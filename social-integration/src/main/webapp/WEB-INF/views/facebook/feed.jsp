<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Feed</title>
	</head>
	<body>
		<c:url value="/facebook/feed" var="feedUrl"/>
		<form action="${feedUrl}" method="POST">
			<input type="hidden" name="_csrf" value="${_csrf.token}"/>
			<fieldset>
				<legend><spring:message code="text.facebook.feed"/></legend>
				<table style="width: 200px; margin-bottom: 20px; margin-top: 10px">
					<tr> 
						<td> 
							<textarea id="messageId" name="message" rows="2" cols="60"></textarea>
						</td>
					</tr>
					<tr> 
						<td> <input type="submit" value='<spring:message code="button.facebook.post"/>'/>  </td>
					</tr>
				</table>
			</fieldset>		
		</form>
		
		<div style="text-align: center">
		
			<ul style="margin-left: 150px; margin-top: 30px">
				<li>
					<c:forEach items="${feeds}" var="post" varStatus="index">
						<p>
							<c:if test="${not empty post.picture}">
								<img alt="index" src="${post.picture}" align="top">
							</c:if>
							<span title="message">${post.message}</span> - <span title="name">${post.name}</span>
						</p>
					</c:forEach>
				</li>
			</ul>
		
		</div>
		
	</body>
</html>