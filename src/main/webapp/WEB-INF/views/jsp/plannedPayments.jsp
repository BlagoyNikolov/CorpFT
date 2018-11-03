<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.financetracker.entities.PaymentType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Planned Payments | Finance Tracker</title>
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
        <h2>All planned payments</h2>
    </section>
    <section class="content">
        <c:if test="${empty pagedPlannedPayments}">
            <h3><i class="ion ion-information-circled"></i> No planned playments yet</h3>
            <h4>Plan and strategize. Start by adding a new planned playment.</h4>
        </c:if>

        <div style="margin-bottom: 25px">
            <div class="row">
                <div class="col-sm-3">
                    <a href="/addPlannedPayment" type="button" class="btn btn-block btn-primary btn-lg"><i
                            class="ion ion-plus"></i> Add new payment</a>
                </div>
                <div class="col-sm-3">
                    <a href="<c:url value="/main"></c:url>" type="button" class="btn btn-block btn-default btn-lg"><i
                            class="ion ion-android-arrow-back"></i> Back</a>
                </div>
            </div>
        </div>

        <div class="box box-primary">
            <div class="box-body">
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <th>Description</th>
                        <th>Added by</th>
                        <th>Date</th>
                        <th>Amount</th>
                        <th>Account amount</th>
                        <th>Category</th>
                        <th>Edit</th>
                    </tr>
                    <c:forEach items="${pagedPlannedPayments}" var="plannedPayment">
                        <tr>
                            <td width="30%">
                                <p style="font-size: 21px;"><c:out value="${plannedPayment.description }"></c:out></p>
                            </td>

                            <td>
                                <p style="font-size: 21px;"><c:out value="${plannedPayment.insertedBy }"></c:out></p>
                            </td>

                            <fmt:parseDate value="${ plannedPayment.fromDate }" pattern="yyyy-MM-dd'T'HH:mm"
                                           var="parsedDateTime" type="both"/>
                            <td>
                                <p style="font-size: 21px;"><fmt:formatDate pattern="dd.MM.yyyy HH:mm"
                                                                            value="${ parsedDateTime }"/></p>
                            </td>

                            <c:choose>
                                <c:when test="${plannedPayment.paymentType eq 'INCOME'}">
                                    <td style="color: green;">
                                        <p style="font-size: 21px;">+ <fmt:formatNumber value="${plannedPayment.amount}"
                                                                                        minFractionDigits="2"/> <c:out
                                                value="${plannedPayment.currency.currencyId }"></c:out></p>
                                    </td>
                                </c:when>
                                <c:when test="${plannedPayment.paymentType eq 'EXPENSE'}">
                                    <td style="color: red;">
                                        <p style="font-size: 21px;">- <fmt:formatNumber value="${plannedPayment.amount}"
                                                                                        minFractionDigits="2"/> <c:out
                                                value="${plannedPayment.currency.currencyId }"></c:out></p>
                                    </td>
                                </c:when>
                            </c:choose>

                            <c:choose>
                                <c:when test="${plannedPayment.paymentType eq 'INCOME'}">
                                    <td style="color: green;">
                                        <p style="font-size: 21px;">+ <fmt:formatNumber
                                                value="${plannedPayment.accountAmount}"
                                                minFractionDigits="2"/> <c:out value="${plannedPayment.account.currency.currencyId}"></c:out></p>
                                    </td>
                                </c:when>
                                <c:when test="${plannedPayment.paymentType eq 'EXPENSE'}">
                                    <td style="color: red;">
                                        <p style="font-size: 21px;">- <fmt:formatNumber
                                                value="${plannedPayment.accountAmount}"
                                                minFractionDigits="2"/> <c:out value="${plannedPayment.account.currency.currencyId }"></c:out></p>
                                    </td>
                                </c:when>
                            </c:choose>
                            <td>
                                <p style="font-size: 21px;"><c:out value="${plannedPayment.category.name}"></c:out></p>
                            </td>
                            <td>
                                <a href="/payment/${plannedPayment.plannedPaymentId}"><i class="ionicons ion-edit"
                                                                                         style="font-size: 21px;"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix">
                <ul class="pagination pagination-sm no-margin pull-right">
                    <c:forEach begin="1" end="${pages}" var="i">
                        <li><a href="/plannedPayments/${i}"><c:out value="${i}"></c:out></a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </section>
</div>
<div>
    <jsp:include page="footer.jsp"></jsp:include>
</div>

</body>
</html>