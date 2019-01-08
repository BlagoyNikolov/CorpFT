<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="<c:url value='/css/daterangepicker.css'></c:url>">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="<c:url value='/css/select2.min.css'></c:url>">
		
		<script src='<c:url value='/js/moment.min.js'></c:url>'></script>
		<script src='<c:url value='/js/daterangepicker.js'></c:url>'></script>
		<title>Edit Budget | Finance Tracker</title>
		<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
	</head>
	<body>
		<div>
			<jsp:include page="left.jsp"></jsp:include>
		</div>
		<div>
			<jsp:include page="header.jsp"></jsp:include>
		</div>
		
		
		<div class="content-wrapper" style="height: auto">
		 <section class="content-header">
			<h1>Edit budget</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		        <c:url var="url" value='/budgets/${ budget.budgetId }/editBudget'></c:url>
		            <f:form role="form" action="${ url }" method="POST" commandName="newBudget">
		              <div class="box-body">
		               	<c:if test="${editBudget!=null}">
		 					<label style="color: red"><c:out value="${editBudget}"/></label>
	  					</c:if>
			             <div class="form-group">
                  			<label>Name</label>
                 			<f:textarea id="bName" class="form-control" rows="1" placeholder="Enter budget name" name="name" path="name"></f:textarea>
                 			<c:set var="name" value="${ budget.name }" />
                 			<script type="text/javascript">
                 				var budgetName = '${ name }';
								document.getElementById("bName").value = budgetName;
							</script>
               			</div>
			            
		                <div class="form-group">
			                <label>Department</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
			                  <option selected="selected"><c:out value="${ accountName }"></c:out></option>
			                  <c:forEach items="${ accounts }" var="account">
			                	  <option><c:out value="${ account.name }"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
		                 <div class="form-group">
			                <label>Category</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category">
			                <option selected="selected"><c:out value="${ categoryName }"></c:out></option>
			                  <c:forEach items="${ categories }" var="category">
			                	  <option><c:out value="${ category.name }"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
			            <div class="form-group">
							<a href="<c:url value="/addCategory"></c:url>" type="button" class="btn btn-block btn-default" style="width: 30%;"><i class="ion ion-plus"></i> Add new category</a>
						</div>
		                <div class="form-group">
		                  <label>Amount</label>
		                  <f:input type="text" class="form-control" placeholder="Amount" path="initialAmount" value="${ editBudgetAmount }" />
		                </div>
             			 <div class="form-group">
                			<label>Date and time range:</label>
							 <div class="input-group">
                  				<div class="input-group-addon">
                    				<i class="fa fa-clock-o"></i>
                  				</div>
								  <input type="text" class="form-control pull-right" id="reservationtime" name="date">
								  <script type="text/javascript">
									var date = '${ date }';
									document.getElementById("reservationtime").value = date;
								  </script>
							 </div>

						 </div>
              
               			<div class="box-footer">
							<button type="submit" class="btn btn-primary">Save</button>
							<a href='<c:url value='/budgets/${ budgetId }'></c:url>' class="btn btn-default">Cancel</a>
		              	</div>
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
	<script src='<c:url value='/js/select2.full.min.js'></c:url>'></script>
	<script src='<c:url value='/js/daterangepicker.js'></c:url>'></script>
	<!-- I hate you -->
	<script type="text/javascript">
		$(function () {
			$('.select2').select2()
			$('#reservationtime').daterangepicker({ timePicker: false, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A' })
		});
	</script>

	</body>
</html>