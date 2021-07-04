<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/loginPage.css" rel="stylesheet">
<title>Pic Page</title>
</head>
<body>
    <form th:action="@{/users/save}"
    th:object="${user}" method="post"
    enctype="multipart/form-data"
    >
    ...
    <div>
     
    <label>Photos: </label>
    <input type="file" name="image" accept="image/png, image/jpeg" />
     
    </div>
    ...
</form>
</body>
</html>