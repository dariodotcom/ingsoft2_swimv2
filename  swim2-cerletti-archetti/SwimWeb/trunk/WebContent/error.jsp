<%@page import="it.polimi.swim.web.pagesupport.ErrorType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	request.setAttribute("pageTitle", "Error");
	Object errObject = request.getAttribute("errorType");
	ErrorType e = errObject != null ? (ErrorType) errObject
			: ErrorType.GENERIC;
%>
<%@ include file="shared/head.jsp"%>
<body class="swim error">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<h1 id="errorHeading">Ops! Sembra ci sia stato un errore.</h1>
			<p id="errorName">
				Errore:
				<%=e.getErrorName()%>
			</p>
			<p id="errorDescription">
				<%=e.getErrorDescription() %>
			</p>
		</div>
		<%@include file="shared/footer.jsp"%>
	</div>
</body>
</html>