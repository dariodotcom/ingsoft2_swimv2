<%@page import="it.polimi.swim.web.pagesupport.Misc"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	int mark = (Integer) request.getAttribute(Misc.MARK_VALUE);
%>
<span class="marker noselect">
	<%
		for (int i = 0; i < Misc.MAX_MARK; i++) {
			String className = (i < mark ? " full" : "");
	%>
	<a href="javascript:" class="markExpression<%=className%>">&nbsp;</a>
	<%
		}
	%>
</span>