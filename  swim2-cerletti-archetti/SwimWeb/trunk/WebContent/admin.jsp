<%@page import="it.polimi.swim.business.entity.AbilityRequest"%>
<%@page import="it.polimi.swim.business.entity.Ability"%>
<%@page import="java.util.List"%>
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
	List<?> abilityReqList = (List<?>) request
			.getAttribute(Misc.ABILITY_LIST);
	String ctx = request.getContextPath();
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="leftColumn" class="column">
				<ul id="swimSecondaryMenu">
					<%
						for (AdministrationSection s : AdministrationSection.values()) {
							String link = ctx + "/" + s.getSectionIdentifier(), name = s
									.getSectionName();
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
						case REQUEST:
							if (abilityReqList.size() == 0) {
					%>
					<p class="text">Non sono presenti richieste.</p>
					<%
						} else {
					%>
					<p class="paragraph">Richieste:</p>
					<div class="list">
					<%
					for (Object o: abilityReqList){
						AbilityRequest a = (AbilityRequest) o;
						Customer requestAuthor = a.getRequestAuthor();
						String name = a.getAbilityName();
						String description = a.getAbilityDescription();
					}
					%>
					</div>
					<%
						}

							break;
						case MANAGEMENT:
							break;
					%>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>