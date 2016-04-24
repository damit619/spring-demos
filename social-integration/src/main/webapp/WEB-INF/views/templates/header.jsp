<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Header Start -->
<div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    
    <%-- <a class="navbar-brand" href='<c:url value="/social-integration/"/>'><img src="<c:url value="/resources/img/logo_1015048_web.jpg"/>" alt="" /></a> --%>
    <a class="navbar-brand" href='<c:url value="/"/>'>Social Integ</a>
</div>
<!-- /.navbar-header -->

<ul class="nav navbar-top-links navbar-right">
	<!-- /.dropdown -->
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
        </a>
        <ul class="dropdown-menu dropdown-user">
            <li>
	            <a href="#">
		            <i class="fa fa-user fa-fw"></i>
		            <sec:authorize access="fullyAuthenticated">
						${accounts.firstName} ${accounts.lastName}
					</sec:authorize>
	            </a>
            </li>
            <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
            </li>
            <li class="divider"></li>
            <li><!-- <a href="#" onClick='javascript:logout();'> -->
            	<sec:authorize access="fullyAuthenticated">
					<form method="POST" action='<c:url value="/logout" />'>
					
						<input type="hidden" name="_csrf" value="${_csrf.token}" />
						&nbsp&nbsp<button type="submit" class="btn btn-link"><i class="fa fa-sign-out fa-fw"></i><spring:message code="text.logout.button"/></button>
					</form>
				</sec:authorize>
            </li>
        </ul>
        <!-- /.dropdown-user -->
    </li>
    <!-- /.dropdown -->
</ul>
<!-- /.navbar-top-links -->

<%-- 
			<a href="/social-integration/"><img src="<c:url value="/resources/images/logo_1015048_web.jpg"/>" alt="" /></a>
			<a href="javascript:void(0);" title="Logout"><img src="<c:url value="/resources/images/logout.png"/>" alt="Logout" title="Logout" onclick="javascript:logout();"  /></a>
			<jsp:useBean id="now" class="java.util.Date"/>
	<span style="color: #6D6C6C;"><fmt:formatDate value="${now}" dateStyle="medium" timeStyle="short"  pattern="E, MMM dd, yyyy HH:mm a z"/></span>
 --%>