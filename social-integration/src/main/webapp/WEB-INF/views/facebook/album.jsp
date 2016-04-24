<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>    

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="text.album.photos.title" /></title>
	</head>
	<body>
		<div id="content" style="margin-left: 200px; margin-top: 30px">
			
			<h3><spring:message code="text.your.facebook.photo.album" /> <span >${album.name}</span></h3>
				<c:forEach items="${photos}" var="photo">
					<span>
						<img src="${photo.images[0].source}" align="middle"/>
					</span>		
				</c:forEach>
		</div>
	</body>
</html>