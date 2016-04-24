<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
		
	<title><tiles:insertAttribute name="browser-title" /></title>
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
	
	    <!-- Morris Charts JavaScript -->
	    <script src="<c:url value='/resources/js/plugins/morris/raphael.min.js'/>"></script>
	    <script src="<c:url value='/resources/js/plugins/morris/morris.min.js'/>"></script>
	    <script src="<c:url value='/resources/js/plugins/morris/morris-data.js'/>"></script>
		<c:url value='/' var="contextPath"/>
		<script type="text/javascript" charset="utf-8">
		var contextPath = ${contextPath};
		$.ajaxSetup({
			// Disable caching of AJAX responses
			cache : true
		});

		$(function() {
			$("input, select").uniform();
		});

		function logout(){
			var Backlen = history.length;
			history.go(-Backlen);
			window.location.href=contextPath+"j_spring_security_logout";
		}
	</script>
	
	</head>
	<body>
		<div id="wrapper">
			<!-- Navigation -->
	        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
				<tiles:insertAttribute name="header-content" />
				<tiles:insertAttribute name="left-nav-bar" />
			</nav>
			 <div id="page-wrapper">
			 	<div class="row">
	                <div class="col-lg-12">
	                    <h1 class="page-header"><tiles:insertAttribute name="section-title" /></h1>
	                </div>
	                <!-- /.col-lg-12 -->
            	</div>
            	<div class="row">
            	 	<tiles:insertAttribute name="contentOnly" />
			 	</div>
			 	<div class="row">
            	 	<tiles:insertAttribute name="footer-content" />
			 	</div>
			 	  <!-- /.row -->
			 </div>
			  <!-- /#page-wrapper -->
		</div>
		 <!-- /#wrapper -->
		<%-- <span id="selected_menu" style="display: none;"><tiles:insertAttribute name="show-menu-name" /></span> --%>
	</body>
</html>