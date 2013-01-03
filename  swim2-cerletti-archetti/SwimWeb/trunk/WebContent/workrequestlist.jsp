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

	List<?> workReqList = (List<?>) request
			.getAttribute(Misc.WORKREQUEST_LIST);
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
					<%
						switch (selectedSection) {
						case ACTIVE_REQUESTS:
					%>
					<p class="paragraph">Richieste attive:</p>
					<div class="list">
						<%
							if (workReqList.size() == 0) {
						%>
						<p class="text">Non sono presenti richieste di lavoro attive.</p>
						<%
							} else {
									for (Object o : workReqList) {
										WorkRequest w = (WorkRequest) o;
										Customer sender = w.getSender();
										Ability requiredAbility = w.getRequiredAbility();
									}
								}
						%>
					</div>
					<%
						break;
						case ARCHIVED_REQUESTS:
					%>
					<p class="paragraph">Richieste concluse:</p>
					<div class="list">
						<%
							if (workReqList.size() == 0) {
						%>
						<p class="text">Non sono presenti richieste di lavoro concluse.</p>
						<%
							} else {
									for (Object o : workReqList) {
										WorkRequest w = (WorkRequest) o;
										Customer sender = w.getSender();
										Ability requiredAbility = w.getRequiredAbility();
									}
								}
						%>
					</div>
					<%
						break;
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>