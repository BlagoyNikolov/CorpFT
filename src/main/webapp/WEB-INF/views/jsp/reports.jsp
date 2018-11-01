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
<title>Reports | Finance Tracker</title>
<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value='/css/daterangepicker.css'></c:url>">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="<c:url value='/css/select2.min.css'></c:url>">
		<script src='<c:url value='/js/moment.min.js'></c:url>'></script>
		<script src='<c:url value='/js/daterangepicker.js'></c:url>'></script>
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
			<h2>Reports</h2>
			<h1>All transactions</h1>
		</section>
		
		<section class="content">
			<c:if test="${empty allTransactions }">
				<h3><i class="ion ion-information-circled"></i>  No records yet</h3>
				<h4>Track your expenses and income. Start by adding a new record. After that you can come right back here and view your reports.</h4>
			</c:if>
			
			<div>
	        	<form role="form" action="<c:url value='/reports/filtered'></c:url>" method="get">
	              <div class="row">
					  <div class="col-sm-2" style="display:table-cell; vertical-align:middle; text-align:center">
						  <div class="form-group">
							  <label>Navigation</label><br>
							  <a href="/main" type="button" class="btn btn-block btn-default"><i class="ion ion-android-arrow-back"></i> Back</a>
						  </div>
					  </div>
	            	<div class="col-sm-3" style="display:table-cell; vertical-align:middle; text-align:center">
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
		           </div>
              		<div class="col-sm-2" style="display:table-cell; vertical-align:middle; text-align:center">
						<div class="form-group">
			                <label>Type</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a type" name="type" onchange="myFunction()" id="type" >
			                  <option>All types</option>
			                  <option>EXPENSE</option>
			                  <option>INCOME</option>
			                </select>
			            </div>
	                </div>
              		
              	   <div class="col-sm-2" style="display:table-cell; vertical-align:middle; text-align:center">
		               <div class="form-group">
			                <label>Category</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select a category" name="category" id="category">
			                <option>All categories</option>
			                  <%-- <c:forEach items="${categories }" var="category">
			                	  <option><c:out value="${category.name}"></c:out></option>
			                  </c:forEach> --%>
			                </select>
			            </div>
					</div>
	                
	                <div class="col-sm-2" style="display:table-cell; vertical-align:middle; text-align:center">
	                 <div class="form-group">
			                <label>Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account" id="account">
			            <%--       <option selected="selected"><c:out value="${ sessionScope.accountName }"></c:out></option> --%>
			            	<option>All accounts</option>
			                  <c:forEach items="${allAccounts}" var="account">
			                	  <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
	                </div>
	                 <div class="col-sm-1" style="display:table-cell; vertical-align:middle; text-align:center">
	                 	<div class="form-group">
	                 		<label>Search</label><br>
		                	<button type="submit" class="btn btn-default">Search</button>
		             	</div>
		             </div>
		          </div>
		        </form>
		     </div>

			<div class="box box-primary">
				<div class="box-body">
					<table class="table table-bordered">
						<tbody>
						<tr>
							<th>Description</th>
							<th>Date</th>
							<th>Amount (USD)</th>
							<th>Category</th>
							<th>Edit</th>
						</tr>
						<c:forEach items="${pagedTransactions}" var="transaction">
							<tr>
								<td>
									<p style="font-size: 21px;"><c:out value="${transaction.description }"></c:out></p>
								</td>

								<fmt:parseDate value="${ transaction.date }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
								<td>
									<p style="font-size: 21px;"><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" /></p>
								</td>

								<c:choose>
									<c:when test="${transaction.type eq 'INCOME'}">
										<td style="color: green;">
											<p style="font-size: 21px;">+ <fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></p>
										</td>
									</c:when>
									<c:when test="${transaction.type eq 'EXPENSE'}">
										<td style="color: red;">
											<p style="font-size: 21px;">- <fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></p>
										</td>
									</c:when>
								</c:choose>
								<td>
									<p style="font-size: 21px;"><c:out value="${transaction.categoryName}"></c:out></p>
								</td>
								<td>
									<a href="/account/transaction/${transaction.transactionId}"><i class="ionicons ion-edit" style="font-size: 21px;"></i></a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- /.box-body -->
				<div class="box-footer clearfix">
					<ul class="pagination pagination-sm no-margin pull-right">
						<c:choose>
							<c:when test="${filtered eq 'true'}">
								<c:forEach begin="1" end="${pages}" var="i">
									<%--<li><a href="/reports/filtered/${i}"><c:out value="${i}"></c:out></a></li>--%>
									<li>
										<form action="/reports/filtered/${i}" method="get" id="pageForm">
											<input type="hidden" value="" name="dateFiler" id="dateReport${i}">
											<input type="hidden" value="" name="typeFiler" id="typeReport${i}">
											<input type="hidden" value="" name="categoryFiler" id="categoryReport${i}">
											<input type="hidden" value="" name="accountFiler" id="accountReport${i}">

											<button type="submit" onclick="reportFunction(${i})"><c:out value="${i}"></c:out></button>
										</form>
									</li>
								</c:forEach>
							</c:when>
							<c:when test="${filtered eq 'false'}">
								<c:forEach begin="1" end="${pages}" var="i">
									<li><a href="/reports/${i}"><c:out value="${i}"></c:out></a></li>
								</c:forEach>
							</c:when>
						</c:choose>

					</ul>
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
		
		function myFunction() {
		    var request = new XMLHttpRequest();
		    var select = document.getElementById("type");
		    var sel = select.value;
		    
		    request.onreadystatechange = function() {
		    	if (this.readyState == 4 && this.status == 200) {
					var select = document.getElementById("category");
					var categories = JSON.parse(this.responseText);
					
					$(select).html(""); //reset child options
			    	$(select).append("<option>All categories</option>");
				    $(categories).each(function (i) { //populate child options 
				        $(select).append("<option>"+categories[i]+"</option>");
				    });
				}
		    };

            request.open("GET", "/account/getCategory/"+sel);
		    request.send();
		}

		function reportFunction(page) {
            var date = $('#reservationtime').val();
            var type = $('#type').val();
            var category = $('#category').val();
            var account = $('#account').val();

            $("#dateReport"+page).val(date);
            $("#typeReport"+page).val(type);
            $("#categoryReport"+page).val(category);
            $("#accountReport"+page).val(account);
        }
		
	</script>
</body>
</html>