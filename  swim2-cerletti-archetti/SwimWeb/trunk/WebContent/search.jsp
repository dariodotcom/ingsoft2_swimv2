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
					<div class="propertyList reducedWidth">
						<div class="property">
							<label class="propertyName" for="name">Nome</label>
							<div class="propertyValue">
								<input type="text" id="name" name="name" class="inputtext" />
							</div>
						</div>
						<div class="property">
							<label class="propertyName" class="inputLabel" for="surname">Cognome</label>
							<div class="propertyValue">
								<input type="text" id="surname" name="surname" class="inputtext" />
							</div>
						</div>
						<div class="property">
							<label class="propertyName" class="inputLabel" for="location">Luogo</label>
							<div class="propertyValue">
								<input type="text" id="location" name="location"
									class="inputtext" />
							</div>
						</div>
						<div class="property">
							<label class="propertyName" class="inputLabel" for="ability">Professionalit&agrave;
								richiesta*</label>
							<div class="propertyValue">
								<input type="text" id="ability" name="ability" class="inputtext abilityInput" />
							</div>
						</div>
						<%
							if (SwimServlet.isCustomerLoggedIn(session)) {
						%>
						<div class="property">
							<span class="propertyName"> <input type="checkbox"
								name="filter" id="filter" value="filter" /><label for="filter">Cerca
									solo tra gli amici</label>
							</span>
						</div>
						<%
							}
						%>
						<div class="submitLine">
							<input type="submit" value="Cerca" class="inputsubmit" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>