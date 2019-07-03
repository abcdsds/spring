<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html> 
<body> 
<script type="text/javascript"> 
var message = '${msg}'; 
var returnUrl = '${url}'; 
alert(message); 
document.location.href = returnUrl; 
</script></body></html> 