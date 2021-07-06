<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="/css/loginPage.css">
 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    
    
<style>
body{
background-image: url(imgs/pharm1.jpg);
background-repeat:no-repeat;
background-size: cover;
background-attachment: fixed;
}
.container{

</style>

    
</head>
<body class="img js-fullheight" >
<section class="ftco-section">
        <div class="container">
            <div class="row justify-content-center">
            <div class="title-section text-center col-12">
            <h2 class="text-uppercase"style="color:white" >Thank you for register</h2>
            <h6 class="text-uppercase" style="color:white;margin:10px ,10px"> An Email Been sent to :</h6>
        </div>
    <form action="tokenpost" method="POST" style="width:40vw; text-align:center">
        <label for="tokenFromUser" class="form-control" ><b>Insert Your Verification code</b>
            </label> <input type="text" id="tokenFromUser"  class="form-control" name="tokenFromUser">
        <input type="hidden" name="${_csrf.parameterName}"
            value="${_csrf.token}" /> <input type="submit" value="Submit Code" class="form-control btn btn-primary submit px-3"style="margin-top:10px;">
<div style="height:400px;">
</div>

    </form>
    </div>
    </div>
    </section>
    

    <script src="js/jquery.min.js"></script>
  <script src="js/popper.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>

    </body>
</html>