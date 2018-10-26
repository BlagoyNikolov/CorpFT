<%@page import="com.financetracker.model.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Header | Finance Tracker</title>
    <link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" type="text/css">
    <!-- Font Awesome -->
    <link href="<c:url value="/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
    <!-- Ionicons -->
    <link href="<c:url value="/css/ionicons.min.css" />" rel="stylesheet" type="text/css">
    <!-- Theme style -->
    <link href="<c:url value="/css/AdminLTE.min.css" />" rel="stylesheet" type="text/css">
    <!-- iCheck -->
    <link href="<c:url value="/css/_all-skins.min.css" />" rel="stylesheet" type="text/css">
    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition skin-blue layout-top-nav">
<div class="wrapper">
    <header class="main-header">
        <!-- Logo -->
        <a href="<c:url value="/main"></c:url>" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>F</b>TR</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Finance</b>Tracker</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <div class="collapse navbar-collapse pull-right">
                <ul class="nav navbar-nav">
                    <!-- User Account: style can be found in dropdown.less -->
                    <li>
                        <form role="form" action="/search" method="get">
                            <div class="input-group margin" style="width: 250px;">
                                <input type="text" class="form-control" placeholder="Search for info here..."
                                       name="keyword">
                                <span class="input-group-btn">
                                    <button type="submit" class="btn btn-dark"><i
                                            class="glyphicon glyphicon-search"></i></button>
                                </span>
                            </div>
                        </form>
                    </li>
                    <li>
                        <a href="<c:url value="/user"></c:url>" style="font-size: 20px;">
                            <i class="ion ion-person"></i>
                            <% User u = (User) request.getSession().getAttribute("user"); %>
                            <span><%= u.getFirstName() %></span>
                        </a>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                    <li>
                        <a href="<c:url value="/logout"></c:url>" style="font-size: 20px;"><i
                                class="glyphicon glyphicon-log-out"></i> Log out</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
</div>
<!-- jQuery 3 -->
<script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<c:url value="/js/bootstrap.min.js" />" type="text/javascript"></script>
<!-- SlimScroll -->
<script src="<c:url value="/js/jquery.slimscroll.min.js" />" type="text/javascript"></script>
<!-- FastClick -->
<script src="<c:url value="/js/fastclick.js" />" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/js/adminlte.min.js" />" type="text/javascript"></script>
<!-- AdminLTE for demo purposes -->
<script src="<c:url value="/js/demo.js" />" type="text/javascript"></script>
</body>
</html>