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
        <title>Budget Info | Finance Tracker</title>
        <link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
    </head>
    <body>
        <c:if test="${ sessionScope.user == null }">
            <c:redirect url="login.jsp"></c:redirect>
        </c:if>

        <div>
            <jsp:include page="left.jsp"></jsp:include>
        </div>
        <div>
            <jsp:include page="header.jsp"></jsp:include>
        </div>

        <div class="content-wrapper">
            <section class="content">
                <div style="margin-bottom: 25px">
                    <div class="row">
                        <c:if test="${(user.isAdmin)}">
                            <div class="col-sm-3">
                                <a href="<c:url value='/budgets/${ budgetId }/editBudget'></c:url>" type="button" class="btn btn-block btn-primary btn-lg"><i class="fa fa-edit"></i> Edit budget</a>
                            </div>

                            <form action="/budgets/${ budgetId }/delete" method="post" id="deleteForm">
                                <div class="col-sm-3">
                                    <input id="submitBtn" type="button" name="btn" data-toggle="modal" data-target="#confirm-submit" class="btn btn-block btn-danger btn-lg" value="Delete Budget">
                                </div>
                            </form>
                        </c:if>

                        <div class="col-sm-3">
                            <%--<a href="<c:url value='/budgets'></c:url>" type="button" class="btn btn-block btn-default btn-lg"><i class="ion ion-android-arrow-back"></i> Back</a>--%>
                            <a href="/budgets" type="button" class="btn btn-block btn-default btn-lg"><i class="ion ion-android-arrow-back"></i> Back</a>
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
                                <th>Amount (USD)</th>
                                <th>Category</th>
                            </tr>
                            <c:forEach items="${pagedTransactions}" var="transaction">
                                <tr>
                                    <td>
                                        <p style="font-size: 21px;"><c:out value="${transaction.description }"></c:out></p>
                                    </td>

                                    <td>
                                        <p style="font-size: 21px;"><c:out value="${transaction.insertedBy }"></c:out></p>
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
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->
                    <div class="box-footer clearfix">
                        <ul class="pagination pagination-sm no-margin pull-right">
                            <c:forEach begin="1" end="${pages}" var="i">
                                <li><a href="/budgets/${budgetId}/${i}"><c:out value="${i}"></c:out></a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </section>
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
                        <h4>Are you sure you want to delete this budget and all the data it contains?</h4>
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
    </body>
</html>