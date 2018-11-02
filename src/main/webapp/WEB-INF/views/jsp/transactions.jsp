<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Transactions | Finance Tracker</title>
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
        <h2><c:out value="${accountName}"></c:out></h2>
        <h1>Current balance <c:out value="${balance}"></c:out></h1>
    </section>
    <section class="content">
        <c:if test="${empty pagedTransactions }">
            <h3><i class="ion ion-information-circled"></i> No records yet</h3>
            <h4>Track your expenses and income. Start by adding a new record.</h4>
        </c:if>

        <div style="margin-bottom: 25px">
            <div class="row">
                <div class="col-sm-3">
                    <a href="/account/addTransaction" type="button" class="btn btn-block btn-primary btn-lg"><i
                            class="ion ion-plus"></i> Add new record</a>
                </div>
                <div class="col-sm-3">
                    <a href="<c:url value="/main"></c:url>" type="button" class="btn btn-block btn-default btn-lg"><i
                            class="ion ion-android-arrow-back"></i> Back</a>
                </div>
                <div class="col-sm-3">
                    <a href="/account/transfer/accountId/${accountId}" type="button"
                       class="btn btn-block btn-default btn-lg"><i class="ion ion-arrow-swap"></i> Transfer</a>
                </div>

                <c:if test="${(user.isAdmin)}">
                    <form action="/account/deleteAccount/${accountId}" method="post" id="deleteForm">
                        <div class="col-sm-3">
                            <input id="submitBtn" type="button" name="btn" data-toggle="modal" data-target="#confirm-submit"
                                   class="btn btn-block btn-danger btn-lg" value="Delete Account"></input>
                        </div>
                    </form>
                </c:if>

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
                        <th>Edit</th>
                    </tr>
                    <c:forEach items="${pagedTransactions}" var="transaction">
                        <tr>
                            <td>
                                <p style="font-size: 21px;"><c:out value="${transaction.description }"></c:out></p>
                            </td>

                            <td>
                                <p style="font-size: 21px;"><c:out value="${transaction.insertedBy }"></c:out></p>
                            </td>

                            <fmt:parseDate value="${ transaction.date }" pattern="yyyy-MM-dd'T'HH:mm"
                                           var="parsedDateTime" type="both"/>
                            <td>
                                <p style="font-size: 21px;"><fmt:formatDate pattern="dd.MM.yyyy HH:mm"
                                                                            value="${ parsedDateTime }"/></p>
                            </td>

                            <c:choose>
                                <c:when test="${transaction.type eq 'INCOME'}">
                                    <td style="color: green;">
                                        <p style="font-size: 21px;">+ <fmt:formatNumber value="${transaction.amount}"
                                                                                        minFractionDigits="2"/></p>
                                    </td>
                                </c:when>
                                <c:when test="${transaction.type eq 'EXPENSE'}">
                                    <td style="color: red;">
                                        <p style="font-size: 21px;">- <fmt:formatNumber value="${transaction.amount}"
                                                                                        minFractionDigits="2"/></p>
                                    </td>
                                </c:when>
                            </c:choose>
                            <td>
                                <p style="font-size: 21px;"><c:out value="${transaction.categoryName}"></c:out></p>
                            </td>
                            <td>
                                <a href="/account/transaction/${transaction.transactionId}"><i class="ionicons ion-edit"
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
                        <li><a href="/account/${accountId}/${i}"><c:out value="${i}"></c:out></a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
</div>
</section>
</div>
<div>
    <jsp:include page="footer.jsp"></jsp:include>
</div>

<div class="modal fade in" id="confirm-submit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h3 class="modal-title">Confirm delete</h3>
            </div>
            <div class="modal-body">
                <h4>Are you sure you want to delete this account and all the data it contains?</h4>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Back</button>
                <a href="#" id="submit" class="btn btn-danger danger">Delete</a>
            </div>
        </div>
    </div>
</div>

<script>
  $('#submit').click(function () {
    /* when the submit button in the modal is clicked, submit the form */
    $('#deleteForm').submit();
  });
</script>

</body>
</html>