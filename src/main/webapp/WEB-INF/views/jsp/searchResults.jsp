<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Results | Finance Tracker</title>
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
        <h2>Search results</h2>
    </section>
    <section class="content">
        <div style="margin-bottom: 25px">
            <div class="row">
                <div class="col-sm-3">
                    <a href="<c:url value="/main"></c:url>" type="button" class="btn btn-block btn-default btn-lg"><i class="ion ion-android-arrow-back"></i> Back</a>
                </div>
            </div>
        </div>

        <h2>Transactions</h2>
        <c:if test="${ empty transactions }">
            <h3><i class="ion ion-information-circled"></i>  No records yet</h3>
        </c:if>
        <c:forEach items="${transactions }" var="transaction">
            <div>
                <a href="account/transaction/${transaction.transactionId}">
                    <div class="info-box" style="width: auto;">
                        <div class="info-box-content">
                            <div class="row">
                                <div class="col-sm-4">
                                    <h4>Description: <c:out value="${transaction.description }"></c:out></h4>
                                </div>
                                <div class="col-sm-4">
                                    <fmt:parseDate value="${ transaction.date }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                    <h4>Date: <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" /></h4>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-4">
                                    <c:choose>
                                        <c:when test="${transaction.type eq 'INCOME'}">
                                            <h3 style="color: green;">Amount: + <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></h3>
                                        </c:when>
                                        <c:when test="${transaction.type eq 'EXPENSE'}">
                                            <h3 style="color: red;">Amount: - <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3>Amount: <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${transaction.amount}" minFractionDigits="2"/></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-sm-4">
                                    <h3>Category: <c:out value="${transaction.categoryName}"></c:out></h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>

        <h2>Planned Payments</h2>
        <c:if test="${ empty plannedPayments }">
            <h3><i class="ion ion-information-circled"></i>  No records yet</h3>
        </c:if>
        <c:forEach items="${plannedPayments}" var="payment">
            <div>
                <a href="payment/${payment.plannedPaymentId}">
                    <div class="info-box" style="width: auto;">
                        <div class="info-box-content">
                            <div class="row">
                                <div class="col-sm-4">
                                    <h3>Name: <c:out value="${payment.name}"></c:out></h3>
                                </div>
                                <div class="col-sm-4">
                                    <fmt:parseDate value="${ payment.fromDate }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                    <h4>Will occur on: <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" /></h4>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-4">
                                    <c:choose>
                                        <c:when test="${payment.paymentType eq 'INCOME'}">
                                            <h3 style="color: green;">Amount: + <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${payment.amount}" minFractionDigits="2"/></h3>
                                        </c:when>
                                        <c:when test="${payment.paymentType eq 'EXPENSE'}">
                                            <h3 style="color: red;">Amount: - <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${payment.amount}" minFractionDigits="2"/></h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3>Amount: <i class="ion-social-usd" style="font-size: 20px;"> </i><fmt:formatNumber value="${payment.amount}" minFractionDigits="2"/></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="col-sm-4">
                                    <h4>Category: <c:out value="${payment.category.name}"></c:out></h4>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>

        <h2>Budgets</h2>
        <c:if test="${ empty budgets }">
            <h3><i class="ion ion-information-circled"></i>  No records yet</h3>
        </c:if>
        <c:forEach items="${ budgets }" var="budget">
            <div class="row">
                <div class="col-md-4" style="width: 50%">

                    <div class="info-box" style="background-color: white" >
                        <a href="budgets/${ budget.key.budgetId }" style="color: #FFFFFF; text-decoration: none !important; margin-bottom: 0px">
                            <span class="info-box-icon"><i class="ion ion-information-circled" style="margin-top: 20px"></i></span>
                        </a>
                        <div class="info-box-content">
                            <span class="info-box-text">Budget name: <c:out value="${ budget.key.name }"></c:out></span>
                            <span class="info-box-number">Initial amount: <c:out value="${ budget.key.initialAmount }"></c:out></span>

                            <div class="progress" style="height: 10px; margin-left: 2px; margin-right: 2px; border-radius: 4px;">
                                <c:set var = "percent" scope = "session" value = "${ budget.value }"/>

                                <c:if test="${ percent >= 100 }">
                                    <div class="progress-bar" style="height: 10px; background-color: red; width: <c:out value = "${ percent }"/>%"></div>
                                </c:if>
                                <c:if test="${ percent < 100 }">
                                    <div class="progress-bar" style="height: 10px; background-color: green; width: <c:out value = "${ percent }"/>%"></div>
                                </c:if>
                            </div>
                            <div class="row">
                                <div class="col-sm-7">
						              <span class="progress-description" style="font-size: 18px">
						                    <c:out value="Amount ${ budget.key.amount } $"></c:out>
						              </span>
                                </div>
                                <div class="col-sm-4">
						              <span class="progress-description" style="float:right; font-size: 18px">
						                    <c:out value="Spent ${ percent }%"></c:out>
						              </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </section>
</div>
<div>
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>