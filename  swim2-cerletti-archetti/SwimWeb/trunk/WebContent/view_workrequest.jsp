<%@page import="it.polimi.swim.business.entity.Ability"%>
<%@page import="it.polimi.swim.business.entity.WorkRequest"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	WorkRequest workReq = (WorkRequest) request
			.getAttribute(Misc.TARGET_WORKREQUEST);
	Customer sender = workReq.getSender();
	Ability requiredAbility = workReq.getRequiredAbility();
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="pageHeading">
				<h1 class="pageTitle">Richiesta di lavoro</h1>
			</div>
			<div class="propertyList">
				<div class="property">
					<span class="propertyName">Da:</span>
					<div class="propertyValue">
						<%=sender.getName()%>
						<%=sender.getSurname()%>
					</div>
				</div>
				<div class="property">
					<span class="propertyName">Per abilità:</span>
					<div class="propertyValue">
						<%=requiredAbility.getName()%>
					</div>
				</div>
				<div class="property">
					<span class="propertyName">Inizio:</span>
					<div class="propertyValue">
						<%=Misc.DATE_TIME_FORMAT.format(workReq.getStartDate())%>
					</div>
				</div>
				<div class="property">
					<span class="propertyName">Fine:</span>
					<div class="propertyValue">
						<%
							String endDateTime = Misc.DATE_TIME_FORMAT.format(workReq
									.getEndDate());
							if (endDateTime == null) {
						%>
						Non impostato
						<%
							} else {
						%>
						<%=endDateTime%>
						<%
							}
						%>
					</div>
				</div>
				<div class="property">
					<span class="propertyName">Luogo:</span>
					<div class="propertyValue">
						<%=workReq.getLocation()%>
					</div>
				</div>
				<div class="property">
					<span class="propertyName">Descrizione:</span>
					<div class="propertyValue">
						<%=workReq.getDescription()%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>