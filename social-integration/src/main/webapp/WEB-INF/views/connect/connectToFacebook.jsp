<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connect to Facebook</title>
</head>
<body>
	<div id="content" style="text-align: center; display: block">
		<h3>Connect to Facebook</h3>
		
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