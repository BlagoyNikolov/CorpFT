<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.financetracker.entities.PaymentType" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transfer | Finance Tracker</title>
<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
<!-- Select2 -->
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
			<h1>Transfer</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		            <form role="form" action="transfer" method="post" id="submitForm">
		            <c:if test="${error!=null}">
			 			<label style="color: red"><c:out value="${error}"/></label>
		  			 </c:if>
		              <div class="box-body">
		              <div class="form-group">
		                  <label>Amount</label>
		                  <input type="text" class="form-control" placeholder="Amount" name="amount">
		                </div>
		                <div class="form-group">
			                <label>From Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="fromAccount">
			                <option selected="selected"><c:out value="${ firstAccount.name }"></c:out></option>
			                  <c:forEach items="${ userAccounts }" var="account">
			                	 <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
		                 <div class="form-group">
			                <label>To Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="toAccount">
			                  <c:forEach items="${ userAccounts }" var="account">
			                	 <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
               		  </div>
               		  <div class="box-footer">
		                <input id="submitBtn" type="button" name="btn" data-toggle="modal" data-target="#confirm-submit" class="btn btn-primary" value="Execute"></input>
		                <a href="<c:url value="/account/${sessionScope.accountId}"></c:url>" class="btn btn-default">Cancel</a>
		              </div>
		            </form>
	          	</div>
        	</div>
	 	</section>
	</div>
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>

	<div class="modal fade in" id="confirm-submit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h3 class="modal-title">Confirm transfer</h3>
				</div>
				<div class="modal-body">
					<h4>Are you sure you want to execute the transfer?</h4>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Back</button>
					<a href="#" id="submit" class="btn btn-success success">Proceed</a>
				</div>
			</div>
		</div>
	</div>

	<script>
        $('#submit').click(function(){
            /* when the submit button in the modal is clicked, submit the form */
            $('#submitForm').submit();
        });
	</script>
	
<!-- jQuery 3 -->
<script src="<c:url value="/js/jquery.min.js" />" type ="text/javascript"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<c:url value="/js/bootstrap.min.js" />" type ="text/javascript"></script>
<!-- Select2 -->
<script src="<c:url value="/js/select2.full.min.js" />" type ="text/javascript"></script>
<!-- bootstrap datepicker -->
<script src="<c:url value="/js/bootstrap-datepicker.min.js" />" type ="text/javascript"></script>
<!-- SlimScroll -->
<script src="<c:url value="/js/jquery.slimscroll.min.js" />" type ="text/javascript"></script>
<!-- FastClick -->
<script src="<c:url value="/js/static/fastclick.js" />" type ="text/javascript"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/js/static/adminlte.min.js" />" type ="text/javascript"></script>
<!-- AdminLTE for demo purposes -->
<script src="<c:url value="/js/static/demo.js" />" type ="text/javascript"></script>

<!-- I hate you -->
<script type="text/javascript">
	$(function () {
		$('.select2').select2()
	});
</script>
</body>
</html>