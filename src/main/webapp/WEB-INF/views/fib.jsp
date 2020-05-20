<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "_//WC3//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content = "text/html; charset =UTF-8">
    <title>Fibonacci</title>
</head>
<body>
<h1>${message}</h1>
<h2>How many numbers of fibonacci sequence you want to display?</h2>
<form:form method="post" action = "fib.html" modelAttribute="fib">
    <form:label path="number">number</form:label>
    <form:input path="number"/>
    <br>

    <input type="submit" value="add"/>

</form:form>


</body>
</html>
