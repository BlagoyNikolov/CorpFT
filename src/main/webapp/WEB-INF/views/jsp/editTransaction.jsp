<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.financetracker.entities.PaymentType" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Transaction | Finance Tracker</title>
<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
<!-- Select2 -->
<link href="<c:url value="/css/select2.min.css" />" rel="stylesheet" type="text/css">
<!-- bootstrap datepicker -->
<link href="<c:url value="/css//bootstrap-datepicker.min.css" />" rel="stylesheet" type="text/css">
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
			<h1>Edit transaction</h1>
		</section>
		<section class="content">
			<div class="col-md-6">
		        <div class="box box-primary">
		        	<f:form role="form" commandName="transaction" method="post" action="editTransaction">
		            <%-- <form role="form" action="editTransaction" method="post"> --%>
		             <c:if test="${error!=null}">
			 			<label style="color: red"><c:out value="${error}"/></label>
		  			 </c:if>
		              <div class="box-body">
		              	<div class="form-group">
			                <label>Type</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a type" name="type" onchange="myFunction()" id="typ" >
			                  <option selected="selected"><c:out value="${ editTransactionType }"></c:out></option>
			                  <option>EXPENSE</option>
			                  <option>INCOME</option>
			                  
			                  <script>
								function myFunction() {
								    var request = new XMLHttpRequest();
								    var select = document.getElementById("typ");
								    var sel = select.value;
								    
								    request.onreadystatechange = function() {
								    	if (this.readyState == 4 && this.status == 200) {
											var select = document.getElementById("cat");
											var categories = JSON.parse(this.responseText);
											
											$(select).html(""); //reset child options
										    $(categories).each(function (i) { //populate child options 
										        $(select).append("<option>"+categories[i]+"</option>");
										    });
										}
								    };

                                    request.open("GET", "/account/getCategory/"+sel);
								    request.send();
								}
							  </script>
			                </select>
			            </div>
		                <div class="form-group">
			                <label>Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
			                <option selected="selected"><c:out value="${ editTransactionAccount }"></c:out></option>
			                  <c:forEach items="${sessionScope.accounts }" var="account">
			                	 <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
		                 <div class="form-group">
			                <label>Category</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category" id="cat">
			                	<option selected="selected"><c:out value="${ editTransactionCategory }"></c:out></option>
			                </select>
			            </div>
			            <div class="form-group">
							<a href="<c:url value="/addCategory"></c:url>" type="button" class="btn btn-block btn-default" style="width: 30%;"><i class="ion ion-plus"></i> Add new category</a>
						</div>
		                <div class="form-group">
		                  <label>Amount</label>
		                  <%-- <input type="text" class="form-control" placeholder="Amount" name="amount" value="${ editTransactionAmount }"> --%>
		                  <f:input path="amount" type="text" cssClass="form-control" placeholder="Amount" value="${ editTransactionAmount }"/>
		                </div>
		                <div class="form-group">
		                  <label>Date</label>
               				<div class="input-group date">
	                  			<div class="input-group-addon">
	                    			<i class="fa fa-calendar"></i>
	                  			</div>
	                  			<fmt:parseDate value="${ editTransactionDate }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                  		 		<input type="text" class="form-control pull-right" id="datepicker" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${ parsedDateTime }" />" name="date">
                			</div>
		                </div>
		                <div class="form-group">
                  			<label>Description</label>
                 			<f:textarea id="desc" class="form-control" rows="3" placeholder="Enter transaction description here" path="description" ></f:textarea>
                 			<c:set var="description" value="${ editTransactionDescription }" />
                 			<script type="text/javascript">
                 				var asd = '${description}';
								document.getElementById("desc").value = asd;
							</script>
               			</div>
               		  </div>
               		  <div class="box-footer">
		                <button type="submit" class="btn btn-primary">Save</button>
		                <%--<a href="<c:url value="/account/${sessionScope.accountId}"></c:url>" class="btn btn-default">Cancel</a>--%>
						  <a class="btn btn-default" href="javascript:history.back(1)">Cancel</a>
					  </div>
		            <%-- </form> --%>
		            </f:form>

					<c:if test="${(user.isAdmin)}">
						<form action="deleteTransaction/${transactionId}" method="post" id="deleteForm">
							<div class="box-footer">
								<input id="submitBtn" type="button" name="btn" data-toggle="modal" data-target="#confirm-submit" class="btn btn-danger" value="Delete transaction"></input>
							</div>
						</form>
					</c:if>
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
					<h3 class="modal-title">Confirm delete</h3>
				</div>
				<div class="modal-body">
					<h4>Are you sure you want to delete this transaction and all the data it contains?</h4>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Back</button>
					<a href="#" id="submit" class="btn btn-danger danger">Delete</a>
				</div>
			</div>
		</div>
	</div>

	<script>
        $('#submit').click(function(){
            /* when the submit button in the modal is clicked, submit the form */
            $('#deleteForm').submit();
        });
	</script>

	<script type="text/javascript">
        $(function () {
            $('.select2').select2()
            $('#datepicker').datepicker({
                autoclose: true
            })
        })
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

</body>
</html>