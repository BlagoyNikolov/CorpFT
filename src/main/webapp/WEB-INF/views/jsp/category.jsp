<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.financetracker.entities.User"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "f"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Category | Finance Tracker</title>
<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
<link href="<c:url value="/css/select2.min.css" />" rel="stylesheet" type="text/css">
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
			<h1>Add category</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <f:form action="addCategory" method="POST" commandName="category">
		              <div class="box-body">
			              <c:if test="${error!=null}">
							<label style="color: red"><c:out value="${error}"/></label>
						  </c:if>
		                <div class="form-group">
		                  <label>Category name</label>
		                  <f:input type="text" cssClass="form-control" placeholder="Name" path="name" />
		                </div>
		                <div class="form-group">
			                <label>Type</label>
			                <f:select cssClass="form-control select2" placeholder="Select a type" name="type" path="type" >
			                  <option>EXPENSE</option>
			                  <option>INCOME</option>
			                </f:select>
			                
			                <input type="hidden" name="link" value="${ link }">
			            </div>
		              </div>
		              <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Save</button>
		                <a class="btn btn-default" href="javascript:history.back(1)">Cancel</a> 
		              </div>
		            </f:form>
	          	</div>
        	</div>
		</section>
	</div>
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>

<!-- Select2 -->
<script src="<c:url value="/js/select2.full.min.js" />" type ="text/javascript"></script>

<!-- I hate you -->
<script type="text/javascript">
	$(function () {
		$('.select2').select2()
	});
</script>
</body>
</html>