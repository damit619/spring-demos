<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="text.album.title"/></title>
	</head>
	<body>
		<div id="content" style="margin-left: 200px; margin-top: 30px">
			<h3><spring:message code="text.your.friends"/></h3>
			<ul style="margin-left: 150px; margin-top: 30px">
				<c:forEach items="${albums}" var="album">
					<li>
						<a href='<c:url value="/facebook/albums/"/>${album.id}'> ${album.name} </a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</body>
</html>