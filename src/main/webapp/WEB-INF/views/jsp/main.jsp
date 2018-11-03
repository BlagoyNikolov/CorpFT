<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.financetracker.entities.User"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main | Finance Tracker</title>
 <link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
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
            <h2>Current balance across all accounts: <b style="font-size: 30px"><c:out value="${ balance }"></c:out></b></h2>
            <h1>All accounts</h1>
        </section>

        <section class="content">
            <div class="row">
            <c:forEach items="${ accounts }" var="account">
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <c:if test="${fn:contains(account.name, 'Finance')}">
                        <div class="small-box bg-yellow">
                    </c:if>
                    <c:if test="${fn:contains(account.name, 'Accounting')}">
                        <div class="small-box bg-blue">
                    </c:if>
                    <c:if test="${fn:contains(account.name, 'Logistics')}">
                        <div class="small-box bg-green">
                    </c:if>
                    <c:if test="${fn:contains(account.name, 'Management')}">
                        <div class="small-box bg-red">
                    </c:if>
                    <c:if test="${!fn:contains(account.name, 'Finance') && !fn:contains(account.name, 'Accounting') && !fn:contains(account.name, 'Logistics') && !fn:contains(account.name, 'Management')}">
                        <div class="small-box bg-aqua">
                    </c:if>
                        <div class="inner">
                          <h3></i><fmt:formatNumber value="${account.amount}" minFractionDigits="2"/> <c:out value="${account.currency.currencyId}"></c:out></h3>
                          <p><c:out value="${account.name}"></c:out></p>
                        </div>
                        <div class="icon">
                            <c:if test="${fn:contains(account.name, 'Logistics')}">
                                <div><i class="ion ion-android-plane"></i></div>
                            </c:if>
                            <c:if test="${fn:contains(account.name, 'Finance')}">
                                <div><i class="ion ion-cash"></i></div>
                            </c:if>
                            <c:if test="${fn:contains(account.name, 'Accounting')}">
                                <div><i class="ion ion-social-usd"></i></div>
                            </c:if>
                            <c:if test="${!fn:contains(account.name, 'Logistics') && !fn:contains(account.name, 'Finance') && !fn:contains(account.name, 'Accounting')}">
                                <div><i class="ion ion-pie-graph"></i></div>
                            </c:if>
                        </div>
                        <a href="account/${account.accountId}" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                      </div>
                </div>
            </c:forEach>

            <div class="col-lg-3 col-xs-6">
                  <div class="small-box bg-teal">
                    <div class="inner">
                      <h3>Add</h3>
                      <p>Add new Account</p>
                    </div>
                    <div class="icon">
                     <div> <i class="ion ion-plus"></i></div>
                    </div>
                    <a href="addAccount" class="small-box-footer">Get started <i class="fa fa-arrow-circle-right"></i></a>
                      </div>
                </div>
            </div>

            <div>
                <c:set var="defaultTransactions" value="${ defaultTransactions }" />

                <div >
                    <canvas id="canvas" style="display: block; width: 80%; height: 20%;" width="80%" height="20%" class="chartjs-render-monitor"></canvas>
                </div>

                <script>
                    var presets = window.chartColors;
                    var utils = Samples.utils;
                    var values = '${defaultTransactions}';

                    values = values.replace(/[\{\}']+/g,'')

                    var dates = [];
                    var amounts = [];
                    var keyValue = [];
                    $.each(values.split(","), function(i,e){
                        keyValue.push(e);
                    });

                    for (var i = 0; i < keyValue.length; i++) {
                        var kv = keyValue[i].split("=");
                        dates.push(kv[0]);
                        amounts.push(kv[1]);
                    }

                    var config = {
                        type: 'line',
                        data: {
                            labels: dates,
                            datasets: [{
                                label: "Overall Cashflow",
                                backgroundColor: utils.transparentize(presets.blue),
                                borderColor: window.chartColors.blue,
                                data: amounts,
                                fill: true,
                            }]
                        },
                        options: {
                            responsive: true,
                            title:{
                                display:true,
                                text:'Balance chart'
                            },
                            tooltips: {
                                mode: 'index',
                                intersect: false,
                            },
                            hover: {
                                mode: 'nearest',
                                intersect: true
                            },
                            scales: {
                                xAxes: [{
                                    display: true,
                                    scaleLabel: {
                                        display: true,
                                        labelString: 'Month'
                                    }
                                }],
                                yAxes: [{
                                    display: true,
                                    scaleLabel: {
                                        display: true,
                                        labelString: 'Value'
                                    }
                                }]
                            }
                        }
                    };

                    window.onload = function() {
                        var ctx = document.getElementById("canvas").getContext("2d");
                        window.myLine = new Chart(ctx, config);
                    };

                </script>
            </div>
            </div>
        </section>
    </div>

    <div>
        <jsp:include page="footer.jsp"></jsp:include>
    </div>

    <!-- chartJS utils -->
    <script src="<c:url value="/js/utils.js" />"  type ="text/javascript"></script>

</body>
</html>