<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Pharma Login</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">
<link href="/css/adminstyle.css" rel="stylesheet" type="text/css">
</head>
<body style="width: 970px; height: 800px; margin: 45px auto; background-color: rgb(48, 48, 48);">
	<p><a href="/logout">LogOut</a></p>
	
	<h1>Welcome</h1>

	<br>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Email</th>
				<th>Location</th>
				<th>G locate</th>
				<th>Pass</th>
				<th>plane Pass</th>
				<th>Role Name</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${everyUserInTheDatabase}" var="user">
				<tr>
					<td>${ user.id }</td>
					<td>${user.firstName}${user.moddleName}${user.lastName}</td>
					<td>${nizar.email}</td>
					<td>${user.location}</td>
					<td>${user.googleLocation}</td>
					<td>${user.password}</td>
					<td>${user.confirm}</td>
					<td>${user.userRole.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<br>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>description</th>
				<th>ownerOfProduct</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${everyProductInTheDatabase}" var="product">
				<tr>
					<td>${product.id}</td>
					<td>${product.name}</td>
					<td>${product.description}</td>
					<td>${product.ownerOfProduct.firstName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<br>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>owner name</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${everyRequestInTheDatabase}" var="req">
				<tr>
					<td>${req.id}</td>
					<%--<td>${req.product.name}</td>--%>
					<td>${req.requester.firstName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>