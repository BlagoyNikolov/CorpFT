<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Left | Finance Tracker</title>
    <!-- Tell the browser to be responsive to screen width -->
    <link href="<c:url value="/img/favicon.ico" />" rel="icon" type="image/x-icon">
    <meta
            content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
            name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet" type="text/css">
    <!-- Font Awesome -->
    <link href="<c:url value="/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
    <!-- Ionicons -->
    <link href="<c:url value="/css/ionicons.min.css" />" rel="stylesheet" type="text/css">
    <!-- Theme style -->
    <link href="<c:url value="/css/AdminLTE.min.css" />" rel="stylesheet" type="text/css">
    <!-- iCheck -->
    <link href="<c:url value="/css/_all-skins.min.css" />" rel="stylesheet" type="text/css">
    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition skin-black fixed sidebar-mini">
<div class="wrapper">
    <!-- Left side column. contains the sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header" style="font-size: 18px">MAIN NAVIGATION</li>
                <li>
                    <a href="<c:url value="/main"></c:url>">
                        <i class="fa fa-credit-card"></i>
                        <span style="font-size: 20px">Departments</span>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/plannedPayments"></c:url>">
                        <i class="fa fa-calendar-check-o"></i>
                        <span style="font-size: 20px">Planned Payments</span>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/budgets"></c:url>">
                        <i class="fa fa-money"></i>
                        <span style="font-size: 20px">Budgets</span>
                    </a>
                </li>
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-line-chart"></i>
                        <span style="font-size: 20px">Charts</span>
                        <span class="pull-right-container">
                                <i class="fa fa-angle-left pull-right"></i>
                            </span>
                    </a>
                    <ul class="treeview-menu">
                        <li>
                            <a href="<c:url value="/cashflowStructure"></c:url>">
                                <h4 style="font-size: 16px"><i class="fa fa-pie-chart"></i> Cashflow Structure</h4>
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/incomeVsExpenses"></c:url>">
                                <h4 style="font-size: 16px"><i class="fa fa-adjust"></i> Income vs Expenses</h4>
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/cashflowTrend"></c:url>">
                                <h4 style="font-size: 16px"><i class="fa fa-area-chart"></i> Cashflow Trend</h4>
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="<c:url value="/reports"></c:url>">
                        <i class="fa fa-book"></i>
                        <span style="font-size: 20px">Reports</span>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/employees"></c:url>">
                        <i class="fa fa-users"></i>
                        <span style="font-size: 20px">Employees</span>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/about"></c:url>">
                        <i class="fa fa-question-circle"></i>
                        <span style="font-size: 20px">About</span>
                    </a>
                </li>
            </ul>
        </section>
    </aside>
</div>
</body>
</html>