<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="account.profile.title" /></title>
	</head>
	<body>
		
		<div id="content" style="margin-left: 200px; margin-top: 30px">
			<h3><spring:message code="text.your.profile" /></h3>
				<p><spring:message code="text.hello" />, <span>${profile.firstName}</span>!</p>
			<dl>
				<dt><spring:message code="text.facebook.id" /></dt>
					<dd>${profile.id}</dd>
				<dt><spring:message code="text.facebook.name" /></dt>
					<dd>${profile.name}</dd>
				<dt><spring:message code="text.facebook.email" /></dt>
					<dd>${email}</dd>
			</dl>
			<form id='disconnect' action='<c:url value="/connect/facebook" />' method='post'>
				<input type="hidden" name="_csrf" value="${_csrf.token}" />
				
				<input type="submit" value='<spring:message code="test.button.label" />'>	
				
				<input type="hidden" name="_method" value="delete" />
			</form>
		</div>
		
	</body>
</html>