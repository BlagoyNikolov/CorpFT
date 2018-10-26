<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login | Finance Tracker</title>
 <link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="css/blue.css">
   <!-- Google Font -->
  <link rel="stylesheet" href="css/css">
</head>

<body class="hold-transition login-page" style="background-image:  url(img/bckg1.jpg); overflow:hidden;">
	<div class="login-box">
	  <div class="login-logo">
	    <h1 style="color: white; text-shadow: 0px 0px 15px black;"><b>Finance</b>Tracker</h1>
	  </div>
	  <div class="login-box-body" style="box-shadow: 0px 0px 15px black">
	    <p class="login-box-msg" style="font-size: 18px">Sign in to start your session</p>
	
	    <f:form commandName="user" method="post" action="login">
	      <c:if test="${login!=null}">
			 <label style="color: red"><c:out value="${login}"/></label>
		  </c:if>
	      <div class="form-group has-feedback">
	        <f:input cssClass="form-control" path="username" placeholder="Username"/>
	        <span class="glyphicon glyphicon-user form-control-feedback"></span> 
	      </div>
	      <div class="form-group has-feedback">
	        <f:password cssClass="form-control" path="password" placeholder="Password"/>
	        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
	      </div>
	      <div class="form-group has-feedback">
		  	<button type="submit" class="btn btn-primary btn-block btn-flat" >Sign In</button>
	      </div>
	    </f:form>

	    <a href='<c:url value='/forgottenPassword'></c:url>'><i class="ionicons ion-help-circled"></i> I forgot my password</a><br>
   		<a href="register"><i class="ionicons ion-android-person-add"></i> Register a new membership</a>
	   </div>
	 </div>
</body>
</html>