<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>About | Finance Tracker</title>
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
				<span style="font-size: 35px"><b>Finance</b>Tracker</span> </section>
			<section class="content">
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title" style="font-size: 22px">About the project</h3>
					</div>
					<div class="box-body">
						<div>
							<p style="font-size: 20px">
								The <b>Finance</b>Tracker is a web based Java Application which
								helps your company manage its departments. It keeps track of each department cash
								inflow, outflow and collects information about the company's financial wealth.
								The app also provides tools that would help its users build a financial plan.
								All of the information is presented via cutting edge technologies in software and design.
							<p>
						</div>
						<div class="row">
							<div class="box-header with-border">
								<h3 class="box-title" style="font-size: 22px">Technologies used</h3>
							</div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="http://www.canoo.com/wp-content/uploads/2017/03/java_ee_logo.png" alt="JEE" width="180" height="auto" align="middle">   
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="http://www.pngall.com/wp-content/uploads/2016/05/MySQL-Logo.png" alt="MySql" width="300" height="auto">  
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="http://vojtechruzicka.com/wp-content/uploads/2016/05/IntelliJIDEA_icon.png" alt="IntelliJ" width="300" height="auto" align="">
						    </div>
						</div>
						<div class="row">
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="https://assets-cdn.github.com/images/modules/logos_page/Octocat.png" alt="GitHub" width="180" height="auto">   
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="https://maven.apache.org/images/maven-logo-black-on-white.png" alt="Maven" width="300" height="auto">  
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="https://bgasparotto.com/wp-content/uploads/2017/12/spring-boot-logo.png" alt="Spring" width="300" height="auto" align="">
						    </div>
						</div>
						<br>
						<div class="row">
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="https://upload.wikimedia.org/wikipedia/commons/2/22/Hibernate_logo_a.png" alt="Hibernate" width="300" height="auto">
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="https://www.shareicon.net/data/512x512/2015/10/24/661304_interface_512x512.png" alt="JSP" width="180" height="auto">
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="http://pluspng.com/img-png/bootstrap-png-bootstrap-512.png" alt="Bootstrap" width="250" height="auto">
						    </div>
						</div>
						<div class="row">
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Badge_js-strict.svg/1000px-Badge_js-strict.svg.png" alt="JS" width="180" height="auto">   
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="http://w3widgets.com/responsive-slider/img/css3.png" alt="CSS" width="180" height="auto">
						    </div>
						    <div class="col-sm-4" style="display:table-cell; vertical-align:middle; text-align:center">
						    	<img src="https://www.w3.org/html/logo/downloads/HTML5_Logo_512.png" alt="HTML" width="250" height="auto">
						    </div>
						</div>
					</div>
				</div>
			</section>
		</div>
		<div>
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
	
		<!-- jQuery 3 -->
		<script src="jquery.min.js"></script>
		<!-- Bootstrap 3.3.7 -->
		<script src="bootstrap.min.js"></script>
		<!-- SlimScroll -->
		<script src="jquery.slimscroll.min.js"></script>
		<!-- FastClick -->
		<script src="fastclick.js"></script>
		<!-- AdminLTE App -->
		<script src="/adminlte.min.js"></script>
		<!-- AdminLTE for demo purposes -->
		<script src="demo.js"></script>
	</body>
</html>