<%@page import="it.polimi.swim.business.bean.QueryResult"%>
<%@page import="it.polimi.swim.web.servlets.SwimServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	QueryResult result = (QueryResult) request
			.getAttribute(Misc.SEARCH_RESULTS);

	Boolean hasResults = (result != null);

	request.setAttribute(Misc.PAGE_TITLE_ATTR, "Ricerca");
%>
<head>
<%@ include file="shared/head.jsp"%>
</head>
<body class="swim search">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="pageHeading">
				<%@include file="shared/messageNotifier.jsp"%>
				<h1 class="pageTitle">Ricerca utenti</h1>
			</div>
			<div class="monoPageContent">
				<%
					if (hasResults) {
				%>
				<h2 class="partTitle">Ricerca</h2>
				<div class="part">
					<%
						}
					%>
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
									<input type="text" id="surname" name="surname"
										class="inputtext" />
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
									<input type="text" id="ability" name="ability"
										class="inputtext abilityInput" />
								</div>
							</div>
							<%
								if (SwimServlet.isCustomerLoggedIn(session)) {
							%>
							<div class="property">
								<span class="propertyName"> <input type="checkbox"
									name="restrict" id="restrict" value="true" /><label
									for="restrict">Cerca solo tra gli amici</label>
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
					<%
						if (hasResults) {
					%>
				</div>
				<h2 class="partTitle">Risultati ricerca:</h2>
				<div class="part">
					<p class="paragraph queryDuration">
						Query eseguita in
						<%=result.getQueryDuration()%>
						millisecondi
					</p>
					<%
						if (result.getResultList().size() == 0) {
					%>
					<p class="paragraph">Non sono stati trovati risultati</p>
					<%
						} else {
					%>
					<div class="propertyList withImage reducedWidth">
						<%
							for (Object o : result.getResultList()) {
										Customer c = (Customer) o;
										String identity = String.format("%s %s", c.getName(),
												c.getSurname());
										String link = context + "/user/?u=" + c.getUsername();
										String thumb = context + Misc.getThumbnailPhotoUrl(c);
						%>
						<div class="property">
							<div class="propertyName">
								<span class="smallImageFrame"> <img alt="userImage"
									src="<%=thumb%>" />
								</span> <span class="afterImage"><%=identity%></span>
							</div>
							<span class="propertyValue"><a href="<%=link%>"
								class="button">Visualizza profilo</a></span>
						</div>
						<%
							}
						%>
					</div>

					<%
						}
					%>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>