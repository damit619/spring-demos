<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="text.friends.page.title"/> </title>
	</head>
	<body>
	
		<div id="content" style="margin-left: 200px; margin-top: 30px">
			<h3><spring:message code="text.your.friends"/></h3>
			<ul style="margin-left: 150px; margin-top: 30px">
				<c:forEach items="${friends}" var="friend">
					<li>
						<img alt="" src="'http://graph.facebook.com/' + ${friend.id} + '/picture'" align="middle"/><span title="name">${friend.name}</span>
					</li>
				</c:forEach>
			</ul>
		</div>
	
	</body>
</html>