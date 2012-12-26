<%@page import="it.polimi.swim.web.servlets.SwimServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	request.setAttribute(Misc.PAGE_TITLE_ATTR, "Ricerca");
%>
<head>
<%@ include file="shared/head.jsp"%>
</head>
<body class="swim search">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="formContainer">
				<form id="searchForm"
					action="<%=request.getContextPath() + "/search/perform"%>"
					method="post">
					<div class="inputLine">
						<label class="inputLabel" for="name">Nome</label> <input
							type="text" id="name" name="name" class="inputtext" />
					</div>
					<div class="inputLine">
						<label class="inputLabel" for="surname">Cognome</label> <input
							type="text" id="surname" name="surname" class="inputtext" />
					</div>
					<div class="inputLine">
						<label class="inputLabel" for="location">Luogo</label> <input
							type="text" id="location" name="location" class="inputtext" />
					</div>
					<div class="inputLine">
						<label class="inputLabel" for="ability">Abilit&agrave;
							richiesta</label> <input type="text" id="ability" name="ability"
							class="inputtext" />
					</div>
					<%
						if (SwimServlet.isUserLoggedIn(session)) {
					%>
					<div class="inputLine">
						<input type="checkbox" name="filter" value="filter" /><label
							for="filter">Cerca solo tra gli amici</label>
					</div>
					<%
						}
					%>
					<div class="inputLine">
						<input type="submit" value="Cerca" class="inputsubmit" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>