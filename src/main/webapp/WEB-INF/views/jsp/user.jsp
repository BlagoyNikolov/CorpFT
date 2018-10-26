<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.financetracker.model.PaymentType" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile | Finance Tracker</title>
<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
</head>
<body>
	<div>
		<jsp:include page="left.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<div class="content-wrapper">
		<section class="content-header">
			<h2>Your profile, <span>${ firstName }</span></h2>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		        <c:url var="url" value='/user/edit'></c:url>
		            <f:form role="form" action="${ url }" method="POST" commandName="user">
		              <div class="box-body">
		              <c:if test="${editUser!=null}">
		 					<label style="color: red"><c:out value="${editUser}"/></label>
	  					</c:if>
	  					<f:hidden path="username" value="${ username }" />
	  					<%-- <f:hidden path="password" value="passddsfs" /> --%>
		                <div class="form-group">
			                <label>Email</label>
               				<f:input type="text" id="email" class="form-control" path="email" />
               				<c:set var="email" value="${ email }" />
                 			<script type="text/javascript">
                 				var asd = '${email}';
								document.getElementById("email").value = asd;
							</script>
			            </div>
		                 <div class="form-group">
			                <label>First name</label>
               				<f:input type="text" id="firstName" class="form-control" path="firstName" />
               				<c:set var="firstName" value="${ firstName }" />
                 			<script type="text/javascript">
                 				var asd = '${firstName}';
								document.getElementById("firstName").value = asd;
							</script>
			            </div>
		                <div class="form-group">
		                  <label>Last name</label>
               				<f:input type="text" id="lastName" class="form-control" path="lastName" />
               				<c:set var="lastName" value="${ lastName }" />
                 			<script type="text/javascript">
                 				var asd = '${lastName}';
								document.getElementById("lastName").value = asd;
							</script>
		                </div>
		                
		                <div class="form-group">
		                  <label>Enter password to confirm your update</label>
               				<f:input type="password" id="password" class="form-control" path="password" />
		                </div>
               		  </div>
               		  <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Update</button>
		                <a href="main" class="btn btn-default">Cancel</a>
		              </div>
		            </f:form>
	          	</div>
        	</div>
		</section>
	</div>
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
</body>
</html>