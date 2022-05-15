<%@page import="com.PaymentManagement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/PaymentManagement.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payment Management</h1>

				<form id="formPaymentManagement" name="formPaymentManagement" method="post" action="PaymentManagement.jsp">


					Payment ID: <input id="pid" name="pid" type="text"
						class="form-control form-control-sm"> 
						
						<br>Account: <input id="acc" name="acc" type="text"
						class="form-control form-control-sm"> 
						
						<br>Unit: <input id="unit" name="unit" type="text"
						class="form-control form-control-sm"> 
						
						
						
						<br> Price: <input id="price" name="price" type="text"
						class="form-control form-control-sm"> 
						
						
						
						<br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidProjectIDSave" name="hidProjectIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divProjectGrid">
					<%
					PaymentManagement projectObj = new PaymentManagement();
						out.print(projectObj.readProject());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
