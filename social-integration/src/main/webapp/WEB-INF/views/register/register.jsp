<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Register</title>

<!-- Bootstrap Core CSS -->
<link href='<c:url value="/resources/css/bootstrap.min.css"/>'
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href='<c:url value="/resources/css/plugins/metisMenu/metisMenu.min.css"/>'
	rel="stylesheet">

<!-- Timeline CSS -->
<link href='<c:url value="/resources/css/plugins/timeline.css"/>'
	rel="stylesheet">

<!-- Custom CSS -->
<link href='<c:url value="/resources/css/sb-admin-2.css"/>'
	rel="stylesheet">

<!-- Morris Charts CSS -->
<link href='<c:url value="/resources/css/plugins/morris.css"/>'
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href='<c:url value="/resources/font-awesome-4.1.0/css/font-awesome.min.css"/>'
	rel="stylesheet" type="text/css">

<!-- Social Buttons CSS -->
<link href='<c:url value="/resources/css/plugins/social-buttons.css"/>'
	rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- jQuery Version 1.11.0 -->
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-1.11.0.js'/>"></script>
<!-- Bootstrap Core JavaScript -->
<script type="text/javascript"
	src="<c:url value='/resources/js/sb-admin-2.js'/>"></script>
<!-- Custom Theme JavaScript -->
<script type="text/javascript"
	src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<!-- Metis Menu Plugin JavaScript -->
<script
	src="<c:url value='/resources/js/plugins/metisMenu/metisMenu.min.js'/>"></script>

</head>
<body>
	<div id="wrapper">
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Ragister</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<%-- <div class="panel-heading">
                           <spring:message code="accounts.personal" />
                        </div> --%>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<form:form method="POST" modelAttribute="accounts" role="form">
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<div class="form-group">
											<form:input path="firstName" cssClass="form-control"
												placeholder="Firstname" />
											<form:errors path="firstName" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<form:input path="lastName" cssClass="form-control"
												placeholder="Lastname" />
											<form:errors path="lastName" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<form:select path="address.country" cssClass="form-control">
												<c:forEach items="${countries}" var="country">
													<form:option value="">Select Country</form:option>
													<form:option value="${country.key}">${country.value}</form:option>
												</c:forEach>
											</form:select>
											<%-- <form:select path="address.country" items="${countries }" cssClass="form-control" /> --%>
											<form:errors path="address.country" cssClass="text-danger" />
										</div>
										<div class="form-group input-group">
											<span class="input-group-addon">@</span>
											<form:input path="emailAddress" class="form-control"
												placeholder="email" />
											<form:errors path="emailAddress" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<form:input path="username" cssClass="form-control"
												placeholder="username" />
											<form:errors path="username" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<form:password path="password" cssClass="form-control"
												placeholder="password" />
											<form:errors path="password" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-default">
												<spring:message code="button.save" />
											</button>
											<button type="reset" class="btn btn-default">Reset</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>


</body>
</html>