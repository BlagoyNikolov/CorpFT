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
<body class="hold-transition register-page" style="background-image:  url(img/bckg4.jpg); overflow:hidden;">
<div class="register-box">
  <div class="register-logo">
    <h1 style="color: white; text-shadow: 0px 0px 15px black;"><b>Finance</b>Tracker</h1>
  </div>

  <div class="register-box-body" style="box-shadow: 0px 0px 15px black">
    <p class="login-box-msg" style="font-size: 18px">You forgot your password? Fear not!</p>

    <form action="<c:url value='/forgottenPassword'></c:url>" method="post">
    	<c:if test="${forgottenPassword!=null}">
		 <label style="color: red"><c:out value="${forgottenPassword}"/></label>
	  	</c:if>
      <div class="form-group has-feedback">
        <input type="email" class="form-control" placeholder="Enter email here to reset your password" required="" name="email" />
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Send email</button>
      </div>
    </form>
      I am already registered. <a href="login" class="text-center"><i class="fa fa-sign-in"></i> Log me in</a>
  </div>
</div>
</body></html>
</html>