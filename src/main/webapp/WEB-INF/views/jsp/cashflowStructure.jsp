<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.financetracker.model.PaymentType" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cashflow Structure | Finance Tracker</title>
<link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value='/css/daterangepicker.css'></c:url>">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="<c:url value='/css/select2.min.css'></c:url>">
		<script src='<c:url value='/js/moment.min.js'></c:url>'></script>
		<script src='<c:url value='/js/daterangepicker.js'></c:url>'></script>
		<script src="<c:url value="/js/Chart.bundle.js" />"  type ="text/javascript"></script>
		<script src="<c:url value="/js/utils.js" />"  type ="text/javascript"></script>
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
			<h2>Cashflow Structure</h2>
			<h3>Filter by</h3>
		</section>
		
		<section class="content">
			<div>
	        	<form role="form" action="getTransactions" method="get">
	              <div class="row">
	              <div class="col-sm-2" style="display:table-cell; vertical-align:middle; text-align:center">
		            	<div class="form-group">
		            		<label>Navigation</label><br>
							<a href="main" type="button" class="btn btn-block btn-default"><i class="ion ion-android-arrow-back"></i> Back</a>
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
              		
              		<div class="col-sm-3" style="display:table-cell; vertical-align:middle; text-align:center">
						<div class="form-group">
				        	<label>Type</label>
							<select class="form-control select2" style="width: 100%;" data-placeholder="Select a type" name="type">
				                  <option>EXPENSE</option>
				                  <option>INCOME</option>
		                    </select>
		                </div>
	                </div>
	                <div class="col-sm-3" style="display:table-cell; vertical-align:middle; text-align:center">
	                 <div class="form-group">
			                <label>Account</label>
			                <select class="form-control select2" style="width: 100%;" data-placeholder="Select an account" name="account">
			                	<option>All accounts</option>
			                  <c:forEach items="${accounts}" var="account">
			                	  <option><c:out value="${account.name}"></c:out></option>
			                  </c:forEach>
			                </select>
			            </div>
	                </div>
	                 <div class="col-sm-1" style="display:table-cell; vertical-align:middle; text-align:center">
	                 	<div class="form-group">
	                 		<label>Filter</label><br>
		                	<button type="submit" class="btn btn-default">Filter</button>
		             	</div>
		             </div>
		          </div>
		        </form>
		     </div>
		
			<div>
			    <c:set var="transactionsCategories" value="${ transactionsCategories }" />
			    
				<div id="canvas-holder" style="width: 70%; margin: 0 auto; height: 100%">
        			<canvas id="chart-area" ></canvas>
   				</div>
				<script>
					var presets = window.chartColors;
					var utils = Samples.utils;
					var cats = '${transactionsCategories}';
					
					cats = cats.replace(/[\{\}']+/g,'')
				    
				    var catsKeyValuePairs = [];
				    var catNames = [];
				    var catValues = [];
					catsKeyValuePairs = cats.split(",");
					//alert(catsKeyValuePairs);
					for (var i = 0; i < catsKeyValuePairs.length; i++) {
						var kv = catsKeyValuePairs[i].split("=");
						catNames.push(kv[0]);
						catValues.push(kv[1]);
					}
					
				/* 	alert(catNames);
					alert(catValues); */

				    var config2 = {
				        type: 'doughnut',
				        data: {
				            datasets: [{
				                //data: [ 48,25,68,7,98,45,48,98,87 ],
				                data: catValues,
				                backgroundColor: [
				                    window.chartColors.red,
				                    window.chartColors.orange,
				                    window.chartColors.yellow,
				                    window.chartColors.green,
				                    window.chartColors.blue,
				                    window.chartColors.purple,
				                    window.chartColors.white
				                    
				                ],
				                label: 'Dataset 1'
				            }],
				           // labels: [
				             //   "Red",
				              //  "Orange",
				             //   "Yellow",
				             //   "Green",
				             //   "Blue","Green1",
				             //   "Blue3","Green2",
				            //    "Blue4"
				            //]
				            labels: catNames
				        },
				        options: {
				            responsive: true,
				            legend: {
				                position: 'top',
				            },
				            title: {
				                display: true,
				                text: 'Cashflow Structure'
				            },
				            animation: {
				                animateScale: true,
				                animateRotate: true
				            }
				        }
				    };

				    window.onload = function() {
				        var ctx2 = document.getElementById("chart-area").getContext("2d");
				        window.myDoughnut = new Chart(ctx2, config2);
				    };
				    
				</script>
			</div>
		</section>
	</div>
	
	
	<div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
	<!-- chartJS utils -->
	<script src="<c:url value="/js/utils.js" />"  type ="text/javascript"></script>
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