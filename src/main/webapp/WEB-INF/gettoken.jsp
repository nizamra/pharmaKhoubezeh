<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/loginPage.css" rel="stylesheet">
<title>Pic Page</title>
</head>
<body>
	<form action="tokenpost" method="POST">
		<label for="tokenFromUser">Insert Your Verification token
			potato:</label> <input type="text" id="tokenFromUser" name="tokenFromUser">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="submit" value="Submit Token">

	</form>
</body>
</html>