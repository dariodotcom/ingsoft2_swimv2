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

	System.out.println(request.getAttribute(Misc.ABILITY_LIST));

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
							String link = ctx + "/admin/" + s.getSectionIdentifier(), name = s
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
					<%@include file="shared/messageNotifier.jsp"%>
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
					<div class="propertyList">
						<%
							for (Object o : abilityReqList) {
										AbilityRequest a = (AbilityRequest) o;
										Customer requestAuthor = a.getRequestAuthor();
										String name = a.getAbilityName();
										String description = a.getAbilityDescription();
										String action = request.getContextPath()
												+ "/admin/respond";
										int id = a.getId();
						%>
						<div id="abilityRequestList" class="property">
							<span class="propertyName"> <span class="bold">Nome:&nbsp;</span><%=a.getAbilityName()%>&emsp;
								<span class="bold">Descrizione:&nbsp;</span><%=a.getAbilityDescription()%>
							</span>
							<div class="propertyValue">
								<a class="button yes" href="javascript:"
									onclick="javascript:quickReply('<%=action%>', this, 'review', {request: <%=id%>, accept: true})">Accetta</a>&nbsp;
								<a class="button no" href="javascript:"
									onclick="javascript:quickReply('<%=action%>', this, 'review', {request: <%=id%>, accept: false})">Rifiuta</a>
							</div>
						</div>

						<%
							}
						%>
					</div>
					<%
						}
							break;
						case MANAGEMENT:
					%>
					<p class="paragraph">Crea una nuova professionlità che sarà
						resa disponibile a tutti gli utenti della piattaforma.</p>
					<form action="<%=ctx%>/admin/create" method="post">
						<div class="propertyList reducedWidth">
							<div class="property">
								<label for="abilityName" class="propertyName">Nome</label>
								<div class="propertyValue">
									<input type="text" name="abilityName" id="abilityName"
										class="inputtext" />
								</div>
							</div>
							<div class="property">
								<label for="abilityDescription" class="propertyName">Descrizione</label>
								<div class="propertyValue">
									<input type="text" name="abilityDescription"
										id="abilityDescription" class="inputtext" />
								</div>
							</div>
							<div class="submitLine">
								<input type=submit class="inputsubmit" value="Aggiungi" />
							</div>
						</div>
					</form>
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