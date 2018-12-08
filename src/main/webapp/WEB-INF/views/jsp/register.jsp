<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri = "http://www.springframework.org/tags/form" prefix = "f"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Register | Finance Tracker</title>
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
    <!-- Select2 -->
    <link href="<c:url value="/css/select2.min.css" />" rel="stylesheet" type="text/css">
</head>
<body class="hold-transition register-page" style="background-image:  url(img/bckg2.jpg); overflow:hidden;">
<div class="register-box">
  <div class="register-logo">
    <h1 style="color: white; text-shadow: 0px 0px 15px black;"><b>Finance</b>Tracker</h1>
  </div>

  <div class="register-box-body" style="box-shadow: 0px 0px 15px black">
    <p class="login-box-msg" style="font-size: 18px">Register a new membership</p>

    <f:form role="form" action="register" method="post" commandName="user">
    	<c:if test="${register!=null}">
		 <label style="color: red"><c:out value="${register}"/></label>
	  	</c:if>
      <div class="form-group has-feedback">
        <f:input type="text" class="form-control" placeholder="Username" required="" path="username" />
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <f:input type="password" class="form-control" placeholder="Password" required="" path="password" />
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Retype password"  name="repeatPassword" required="">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <f:input type="email" class="form-control" placeholder="Email" required="" path="email" />
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <f:input type="text" class="form-control" placeholder="First Name" required="" path="firstName" />
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <f:input type="text" class="form-control" placeholder="Last Name" required="" path="lastName" />
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <select class="form-control select2" name="position">
            <option>Intern</option>
            <option>Accountant</option>
            <option>Marketing manager</option>
            <option>Logistics manager</option>
            <option>Financial manager</option>
            <option>Sales manager</option>
            <option>CTO</option>
            <option>CFO</option>
            <option>CEO</option>
        </select>
      </div>
      <div class="form-group has-feedback">
        <button type="submit" class="btn btn-primary btn-block btn-flat">Register</button>
      </div>
    </f:form>
      I am already registered. <a href="login" class="text-center"><i class="fa fa-sign-in"></i> Log me in</a>
  </div>
</div>
<!-- jQuery 3 -->
<script src="<c:url value="/js/jquery.min.js" />" type="text/javascript"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<c:url value="/js/bootstrap.min.js" />" type="text/javascript"></script>
<!-- Select2 -->
<script src="<c:url value="/js/select2.full.min.js" />" type="text/javascript"></script>
<!-- I hate you -->
<script type="text/javascript">
    $(function () {
        $('.select2').select2()
    });
</script>
</body>
</html>