<%@page
	import="it.polimi.swim.web.servlets.AdministrationServlet.AdministrationSection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	Object ref = request.getAttribute(Misc.SELECTED_SECTION_ATTR);
	AdministrationSection selectedSection = (ref != null ? (AdministrationSection) ref
			: AdministrationSection.REQUEST);
	request.setAttribute(Misc.ABILITY_LIST,
			selectedSection.getSectionName());
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="leftColumn" class="column"></div>
			<div id="rightColumn" class="column"></div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>