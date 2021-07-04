<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Page</title>
</head>
<body>
	<c:if test="${logoutMessage != null}">
		<c:out value="${logoutMessage}"></c:out>
	</c:if>
	<h1>Register!</h1>
	<c:if test="${errorMessage != null}">
		<c:out value="${errorMessage}"></c:out>
	</c:if>
	<p>
		<form:errors path="user.*" />
	</p>

	<form:form method="POST" action="/registration">

		<p>
			<label for="username" class="try">Name:</label> 
			<input type="text" id="username" name="username" />
		</p>
		<p>
			<label for="email" class="try">email</label> 
			<input type="email" id="email" name="email" />
		</p>
		<p>
			<label for="location" class="try">location</label> 
			<input type="text" id="location" name="location" />
		</p>
		<p>
			<label for="password" class="try">password</label> 
			<input type="password" id="password" name="password" />
		</p>
		<p>
			<label for="passwordConfirmation" class="try">passwordConfirmation</label> 
			<input type="password" id="passwordConfirmation" name="passwordConfirmation" />
		</p>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="submit" value="Register!" />
	</form:form>
</body>
</html>