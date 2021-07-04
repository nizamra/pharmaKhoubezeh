<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Page</title>
<link rel="stylesheet" type="text/css" href="/css/loginPage.css">
 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	

</head>
<body class="img js-fullheight" style="background-image: url(imgs/pharm1.jpg);">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<h1 style="color:white;">Pharma KHobeza</h1>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-md-6 col-lg-4">
					<div class="login-wrap p-0">
		      	<h3 class="mb-4 text-center">Register</h3>
		      	<form:form action="/registration" modelAttribute="user" method="POST" class="signin-form">
		      		<div class="form-group">
		      			<form:errors path="username"/>
		      			<form:input path="username" type="text" class="form-control" placeholder="username"/>
		      		</div>
	            <div class="form-group">
				  <form:errors path="email"/>
	              <form:input type="email" class="form-control" path="email" placeholder="Email"/>
	            </div>
	            <div class="form-group">
		      			<form:errors path="location"/>
						<form:select class="form-select padd" path="location">
							<option selected>Location</option>
								<c:forEach items="${ locations }" var="locate">
							<option value="${ locate }">${ locate}</option>
						</c:forEach>
						</form:select>
		      		</div>
	             <div class="form-group">
	              <form:errors path="password"/>
	              <form:input id="password-field" path="password" type="password" class="form-control" placeholder="Password" />
	            </div>
	             <div class="form-group">
	              <form:errors path="passwordConfirmation"/>
	              <form:input id="password-field" path="passwordConfirmation" type="password" class="form-control" placeholder="Confirm Password" name="passwordConfirmation"/>
	            </div>
	            <div class="form-group">
	            	<button type="submit" class="form-control btn btn-primary submit px-3">Sign Up</button>
	            </div>
	            
	          </form:form>
	          <p class="w-100 text-center">&mdash;  Have an account? &mdash;</p>
	          <div class="social d-flex text-center">
	         
	          	<a href="/login" class="px-2 py-2 mr-md-1 rounded"><span class="ion-logo-facebook mr-2"></span> Sign in</a>
	          	
	          </div>
		      </div>
				</div>
			</div>
		</div>
	</section>

	<script src="js/jquery.min.js"></script>
  <script src="js/popper.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>


	</body>


</html>