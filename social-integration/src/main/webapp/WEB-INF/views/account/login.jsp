<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><spring:message code="lable.login.page.title"/></title>

	<!-- Bootstrap Core CSS -->
    <link href='<c:url value="/resources/css/bootstrap.min.css"/>' rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href='<c:url value="/resources/css/plugins/metisMenu/metisMenu.min.css"/>' rel="stylesheet">

    <!-- Timeline CSS -->
    <link href='<c:url value="/resources/css/plugins/timeline.css"/>' rel="stylesheet">

    <!-- Custom CSS -->
    <link href='<c:url value="/resources/css/sb-admin-2.css"/>' rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href='<c:url value="/resources/css/plugins/morris.css"/>' rel="stylesheet">

    <!-- Custom Fonts -->
    <link href='<c:url value="/resources/font-awesome-4.1.0/css/font-awesome.min.css"/>' rel="stylesheet" type="text/css">
	
	<!-- Social Buttons CSS -->
    <link href='<c:url value="/resources/css/plugins/social-buttons.css"/>' rel="stylesheet">
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		
		<!-- jQuery Version 1.11.0 -->
		<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.11.0.js'/>"></script>
		 <!-- Bootstrap Core JavaScript -->
		<script type="text/javascript" src="<c:url value='/resources/js/sb-admin-2.js'/>"></script>
		<!-- Custom Theme JavaScript -->
		<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
		<!-- Metis Menu Plugin JavaScript -->
	    <script src="<c:url value='/resources/js/plugins/metisMenu/metisMenu.min.js'/>"></script>
	
	 </head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
            <sec:authorize access="isAnonymous()">
            	<div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action='<c:url value="/login/authenticate" />' method="POST">
                        	<input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                	<c:if test="${param.error eq 'nOk'}">
										<label style="color: red"><spring:message code="text.login.failed"/> ${SPRING_SECURITY_LAST_EXCEPTION.message}</label>
									</c:if>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                    <label>
                                        <a href='<c:url value="/account/register/" />'>Register</a>
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input type="submit" class="btn btn-lg btn-success btn-block" value="Login">
                                <!-- <a href="index.html" class="btn btn-lg btn-success btn-block">Login</a> -->
                            </fieldset>
                        </form>
                         <!-- FACEBOOK SIGNIN -->
						<form name="fb_signin" id="fb_signin" action='<c:url value="/auth/facebook"/>' method="POST">
								<input type="hidden" name="_csrf" value="${_csrf.token}" />
								<!-- ,user_photos,offline_access -->
								<input type="hidden" name="scope" value="read_friendlists,read_stream,publish_stream,user_photos,offline_access" />
								 <div class="form-group">
									<button class="btn btn-block btn-social btn-facebook" type="submit">
									<i class="fa fa-facebook"></i> Sign in with Facebook
									</button>
								</div>
						</form>
                    </div>
                </div>
              </sec:authorize>
            </div>
        </div>
    </div>

</body>

</html>


<%-- <html>
	
<head>

  <meta charset="UTF-8">

  <title><spring:message code="lable.login.page.title"/></title>
	
  	<link rel='stylesheet' href='<spring:url value="/resources/css/jquery-ui.css" />'>

    <link rel="stylesheet" href='<spring:url value="/resources/css/style.css" />' media="screen" type="text/css" />

</head>

<body>
<sec:authorize access="isAnonymous()">
  <div class="login-card">
  <h2><spring:message code="lable.login.form.title"/></h2>
	<c:if test="${param.error eq 'nOk'}">
			<label style="color: red"><spring:message code="text.login.failed"/></label><br/>
			<font color="red"> ${SPRING_SECURITY_LAST_EXCEPTION.message} </font>
	</c:if>
	
	<form action='<c:url value="/login/authenticate" />' method="POST" role="form" id="form1">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    
    <input type="text" name="username" placeholder="Username">
    
    <input type="password" name="password" placeholder="Password">
    
    <input type="submit" name="login" class="login login-submit" value="login">
  </form>

  <div class="login-help">
  	
    <a href='<c:url value="/account/register/" />'>Register</a> &nbsp &nbsp <a href="#">Forgot Password</a>
  </div>
</div>
</sec:authorize>

			
<!-- <div id="error"><img src="https://dl.dropboxusercontent.com/u/23299152/Delete-icon.png" /> Your caps-lock is on.</div> -->
  
  
		
  <script src='<spring:url value="/resources/jquery/jquery_and_jqueryui.js" />'></script>
<!--
    If the user is already authenticated, show a help message instead
    of the login form and social sign in buttons.
	-->
	<sec:authorize access="isAuthenticated()">
	    <p><spring:message code="text.login.authenticated.user.help"/></p>
	</sec:authorize>
</body>

</html> --%>