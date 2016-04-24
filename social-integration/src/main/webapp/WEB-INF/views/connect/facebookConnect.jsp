<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
	<head>
		<title><spring:message code="text.facebook.connect.title" /></title>
	</head>
	<body>
		<div id="content" style="text-align: center; display: block">
			<h3><spring:message code="text.facebook.connect.title" /></h3>
			
			<form action='<c:url value="/connect/facebook" />' method="POST">
			
				<input type="hidden" name="_csrf" value="${_csrf.token}" /> 
				<!-- ,user_photos,offline_access -->
				<input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
			
				<div >
					<p>You aren't connected to Facebook yet. Click the button to
						connect Social Integration with your Facebook account.</p>
				</div>
				<p>
					<button type="submit">
						<img src='<c:url value="/resources/images/social/facebook/connect_light_medium_short.gif" />' />
					</button>
				</p>
				<label for="postToWall"><input id="postToWall" type="checkbox" name="postToWall" /> 
				
				Tell your Facebook wall</label>
			</form>
		</div>
	</body>
</html>