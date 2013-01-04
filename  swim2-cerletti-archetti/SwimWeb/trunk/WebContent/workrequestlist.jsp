<%@page import="it.polimi.swim.business.entity.Ability"%>
<%@page import="it.polimi.swim.business.entity.WorkRequest"%>
<%@page import="java.util.List"%>
<%@page import="it.polimi.swim.web.servlets.CustomerWorkrequestServlet"%>
<%@page
	import="it.polimi.swim.web.servlets.CustomerWorkrequestServlet.CustomerWorkRequestSection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	Object ref = request.getAttribute(Misc.SELECTED_SECTION_ATTR);
	CustomerWorkRequestSection selectedSection = (ref != null ? (CustomerWorkRequestSection) ref
			: CustomerWorkRequestSection.ACTIVE_REQUESTS);

	request.setAttribute(Misc.PAGE_TITLE_ATTR,
			selectedSection.getSectionName());

	List<?> sentList = (List<?>) request
			.getAttribute(Misc.SENT_WORKREQUEST_LIST);

	List<?> receivedList = (List<?>) request
			.getAttribute(Misc.RECEIVED_WORKREQUEST_LIST);
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="leftColumn" class="column">
				<ul id="swimSecondaryMenu">
					<%
						for (CustomerWorkRequestSection s : CustomerWorkRequestSection
								.values()) {
							String link = request.getContextPath() + "/"
									+ CustomerWorkrequestServlet.CONTEXT_NAME + "/"
									+ s.getSectionIdentifier(), name = s.getSectionName();
							String selClass = selectedSection.equals(s) ? " selected" : "";
					%>
					<li class="entry<%=selClass%>"><a class="label"
						href="<%=link%>"><%=name%></a></li>
					<%
						}
					%>
				</ul>
			</div>
			<div id="rightColumn" class="column">
				<div class="pageHeading">
					<h1 class="pageTitle"><%=selectedSection.getSectionName()%></h1>
				</div>
				<div class="monoPageContent">
					<h2 class="partTitle">Richieste ricevute</h2>
					<div class="part">
						<%
							if (receivedList.size() == 0) {
						%>
						<p class="text">Non sono presenti richieste di lavoro attive.</p>
						<%
							} else {
						%>
						<div class="propertyList">
							<%
								for (Object o : receivedList) {
										WorkRequest w = (WorkRequest) o;
										Customer sender = w.getSender();
										Ability requiredAbility = w.getRequiredAbility();
										Customer c = w.getSender();
										String identity = c.getName() + " " + c.getSurname();
										String link = context + "/works/view?w=" + w.getId();
							%>
							<div class="property">
								<div class="propertyName">
									Da:
									<%=identity%></div>
								<div class="propertyValue">
									<a class="button" href="<%=link%>">Visualizza</a>
								</div>
							</div>

							<%
								}
							%>
						</div>
						<%
							}
						%>

					</div>
					<h2 class="partTitle">Richieste inviate</h2>
					<div class="part">
						<%
							if (sentList.size() == 0) {
						%>
						<p class="text">Non sono presenti richieste di lavoro
							concluse.</p>
						<%
							} else {
						%>
						<div class="propertyList">
							<%
								for (Object o : sentList) {
										WorkRequest w = (WorkRequest) o;
										Customer sender = w.getSender();
										Ability requiredAbility = w.getRequiredAbility();
										Customer c = w.getReceiver();
										String identity = c.getName() + " " + c.getSurname();
										String link = context + "/works/view?w=" + w.getId();
							%>
							<div class="property">
								<div class="propertyName">
									A:
									<%=identity%></div>
								<div class="propertyValue">
									<a class="button" href="<%=link%>">Visualizza</a>
								</div>
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
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>