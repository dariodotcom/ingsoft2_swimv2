<%@page import="it.polimi.swim.web.pagesupport.ErrorType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
			<div class="pageHeading">
				<h1 class="pageTitle">Ops! Sembra ci sia stato un errore.</h1>
			</div>
			<div class="monoPageContent">
				<p id="errorName">
					Errore:
					<%=e.getErrorName()%>
				</p>
				<p id="errorDescription">
					<%=e.getErrorDescription()%>
				</p>
			</div>
		</div>
		<%@include file="shared/footer.jsp"%>
	</div>
</body>
</html>