<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="text.rate.limit.title" /></title>
	</head>
	<body>
		<div id="content" style="margin-left: 200px; margin-top: 30px">
			<h2><spring:message code="text.rate.limit.title" /></h2>
			<p>Sorry, but the rate limit for accessing <span> ${providerId} </span> has been exceeded. Please try again later.</p>
		</div>
	</body>
</html>