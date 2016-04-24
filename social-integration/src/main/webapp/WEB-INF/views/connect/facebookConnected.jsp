<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Connected to facebook</title>
</head>
<body>
	<div id="header">
		<h1>
		<c:url value="/" var="home"></c:url>
			<a href="${home}">Home</a>
		</h1>
	</div>
	<div id="leftNav">Left nav menu</div>
	<div >
		<h3>Connected to Facebook</h3>
		<form id="disconnect" method="post">
			<input type="hidden" name="_csrf" value="${_csrf.token}" />
			<div class="formInfo">
				<p>Spring Social Showcase is connected to your Facebook account.
					Click the button if you wish to disconnect.</p>
			</div>
			<button type="submit">Disconnect</button>
			<input type="hidden" name="_method" value="delete" />
		</form>
		<c:url value="/facebook" var="facebook"></c:url>
		<a href="${facebook}">View your Facebook profile</a>
	</div>
</body>
</html>