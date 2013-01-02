<%@page import="it.polimi.swim.web.pagesupport.Misc"%>
<%@page import="it.polimi.swim.web.pagesupport.ErrorType"%>
<%@page import="it.polimi.swim.web.pagesupport.NotificationMessages"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	NotificationMessages notification = (NotificationMessages) request
			.getAttribute(Misc.NOTIFICATION_ATTR);

	ErrorType error = (ErrorType) request.getAttribute(Misc.ERROR_ATTR);

	if (notification != null) {
		//Print notification
%>
<p class="notification">
	<%=notification.getDescription()%>
</p>
<%
	}

	if (error != null) {
		//Print error
%>
<p class="error">
	<%=error.getErrorDescription()%>
</p>
<%
	}
%>