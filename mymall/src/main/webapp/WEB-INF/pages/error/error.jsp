<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误信息</title>
</head>
<body>
<span style="font-size:18px; font-weight:bold">错误信息： ${message}</span>
<form:form commandName="product">  
    <form:errors path="*" cssStyle="color:red"></form:errors><br/>  
</form:form>  
</body>
</html>