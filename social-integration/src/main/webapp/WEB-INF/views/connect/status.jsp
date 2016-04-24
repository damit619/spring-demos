<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
	<head>
		<title><spring:message code="text.status.title"/></title>
	</head>
	<body>
		<div id="content" style="margin-left: 200px; margin-top: 30px">
			<h3><spring:message code="text.connection.status"/></h3>
			<div style="text-align: center">
				<c:forEach items="${providerIds}" var="providerId">
				
					<h4><img src="${providerId}'.icon'" width="36" height="36" /><span>"${providerId.displayName}</span></h4>
					<p>
						<c:if test="${not empty connectionMap[__providerId__]}">
							<span>
								You are connected to <span>${providerId.displayName}</span> as <span >${ connectionMap[__providerId__][0].displayName}</span>.
							</span>
						</c:if>
						<c:if test="${empty connectionMap[__providerId__]}">
						<span >
							You are not yet connected to <span>${providerId.displayName}</span>.
						</span>
						
							Click <a href='<c:url value="/connect/" />${providerId}'>here</a> to manage your <span>${providerId.displayName}</span> connection.
						</c:if>
					</p>
				</c:forEach>
			</div>
		</div>
	</body>
</html>